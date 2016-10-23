package pl.put.poznan;

import lombok.Getter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import pl.put.poznan.RnaMatrix.GreekAngleName;
import pl.put.poznan.Session.SessionData;
import pl.put.poznan.Session.SessionHolder;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author piotrowy
 */
@Path("/options")
public class AppController {

    /**
     * parameter which serves to validate proper length of pdb id.
     */
    private static final int PDB_ID_LENGTH = 4;

    /**
     * logger to log sth.
     */
    private static final Logger LOGGER = Logger.getLogger(AppController.class);

    /**
     * failure message is sent when application can not do computations with given input.
     */
    private static final String FAILURE_MESSAGE = "Entity not found for data: ";

    /**
     * config stores every important param from config.properties file.
     */
    @Getter
    private static ConfigService config;

    /**
     *
     * @throws IOException because ConfigService reads properties from file.
     */
    public AppController() throws IOException {
        config = new ConfigService();
    }

    /**
     *
     * @return sessionMap which stores all session ids and structures mapped to them.
     */
    public static Map<UUID, SessionData> getSessionMap() {
        return SessionHolder.getInstance().getSessionMap();
    }

    /**
     * @param structure which stores pdb file, and all information about RNA structure.
     * @return id of session.
     */
    private String generateSessionData(final StructureContainer structure) {
        UUID id = UUID.randomUUID();
        getSessionMap().put(id, new SessionData(structure, new Date()));
        return id.toString();
    }

    /**
     * @param sessionId to which pdb structure is mapped.
     * @param func which is called after session id validation.
     * @return response created by func function or failure message.
     */
    private Response checkSessionIdAndGetResponse(final String sessionId, final Function<String, Response> func) {
        return getResponse(sessionId, (s) -> !s.equals("")
                && AppController.getSessionMap().containsKey(UUID.fromString(s.toString())), func);
    }

    /**
     * @param pdbId is id of pdb structure.
     * @param func which is called after pdb id validation.
     * @return response created by func function or failure message.
     */
    private Response checkPdbIdAndGetResponse(final String pdbId, final Function<String, Response> func) {
        return getResponse(pdbId, (s) -> !s.equals("") && s.toString().length() == PDB_ID_LENGTH && PdbIdContainer.
                isPdbIdExists((String) s), func);
    }

    /**
     *
     * @param param which is validated with predicate pred.
     * @param pred predicate which serves to validate param.
     * @param func which is called if predicate pred returns true.
     * @return response created by func function or failure message.
     */
    private Response getResponse(final String param, final Predicate pred, final Function<String, Response> func) {
        if (pred.test(param)) {
            return func.apply(param);
        }
        return Response.status(Response.Status.NOT_FOUND).entity(FAILURE_MESSAGE + param).build();
    }

    /**
     *
     * @param structurePDB id of pdb structure.
     * @return session id which is mapped to pdb structure.
     */
    @GET
    @Path("structureId")
    @Produces(MediaType.APPLICATION_JSON)
    public final Response uploadStructure(@QueryParam("structurePDB") final String structurePDB) {
        return checkPdbIdAndGetResponse(structurePDB.toUpperCase(), (s) -> Response.ok(generateSessionData(
                new StructureContainer(s)), MediaType.APPLICATION_JSON).build());
    }

    /**
     *
     * @param uploadedInputStream pdb file uploaded by user.
     * @param fileDetail file details?
     * @return session id mapped to uploaded file.
     */
    @POST
    @Path("structureFile")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_HTML)
    public final Response uploadStructure(@FormDataParam("modelFile") final InputStream uploadedInputStream,
                                          @FormDataParam("modelFile") final FormDataContentDisposition fileDetail) {
        File uploadedFileLocation = null;
        FileOutputStream outputStream = null;

        try {
            uploadedFileLocation = File.createTempFile("RNAsprite", ".pdb");
            outputStream = new FileOutputStream(uploadedFileLocation);
            IOUtils.copy(uploadedInputStream, outputStream);
            return Response.ok(generateSessionData(new StructureContainer(uploadedFileLocation)),
                    MediaType.APPLICATION_JSON).build();
        } catch (Exception ex) {
            LOGGER.warn(ex);
        } finally {
            FileUtils.deleteQuietly(uploadedFileLocation);
            IOUtils.closeQuietly(outputStream);
        }
        return Response.status(Response.Status.NOT_FOUND).entity(FAILURE_MESSAGE).build();
    }

    /**
     *
     * @return list of atom names.
     */
    @GET
    @Path("atoms")
    @Produces(MediaType.APPLICATION_JSON)
    public final Response getAtomsList() {
        return Response.ok(new AtomNamesList(), MediaType.APPLICATION_JSON).build();
    }

    /**
     *
     * @param sessionId mapped to pdb structure which is compute.
     * @return all available chain ids in pdb structure mapped to sessionId.
     */
    @GET
    @Path("chains")
    @Produces(MediaType.APPLICATION_JSON)
    public final Response getChainsList(
            @QueryParam("sessionId") final String sessionId) {
        return checkSessionIdAndGetResponse(sessionId, (s) -> {
            AppController.getSessionMap().get(UUID.fromString(s)).setLastUseTime(new Date());
            return Response.ok(new ChainsIdList(AppController.getSessionMap().get(UUID.fromString(s)).getStructure()),
                    MediaType.APPLICATION_JSON).build();
        });
    }

    /**
     *
     * @param sessionId which is mapped to pdb structure which is compute.
     * @param chain in which application finds residues.
     * @param at1 distance is counted betwee two atoms.
     * @param at2 distance is counted betwee two atoms.
     * @return matrix of distances between two atoms in all residues in specified chain.
     */
    @GET
    @Path("distances")
    @Produces(MediaType.APPLICATION_JSON)
    public final Response getDistanceMatrix(
            @QueryParam("sessionId") final String sessionId,
            @QueryParam("chain") final String chain,
            @QueryParam("at1") final String at1,
            @QueryParam("at2") final String at2) {
        return checkSessionIdAndGetResponse(sessionId, (s) -> {
            AppController.getSessionMap().get(UUID.fromString(s)).setLastUseTime(new Date());
            return Response.ok(new DistanceMatrix(
                    AppController.getSessionMap().get(UUID.fromString(sessionId)).getStructure(),
                    chain, at1, at2), MediaType.APPLICATION_JSON).build();
        });
    }

    /**
     *
     * @param sessionId which is mapped to pdb structure which is compute.
     * @param fragmentsDefinition ???
     * @param paramList ???
     * @param at1 distance is counted betwee two atoms.
     * @param at2 distance is counted betwee two atoms.
     * @return fragment of matrix of distances between two atoms in all residues in specified chain.
     */
    @GET
    @Path("distancesFragment")
    @Produces(MediaType.APPLICATION_JSON)
    public final Response getFragmentOfDistanceMatrix(
            @QueryParam("sessionId") final String sessionId,
            @QueryParam("fragmentsDefinition") final List<String> fragmentsDefinition,
            @QueryParam("paramList") final String paramList,
            @QueryParam("at1") final String at1,
            @QueryParam("at2") final String at2) {
        return checkSessionIdAndGetResponse(sessionId, (s) -> {
            AppController.getSessionMap().get(UUID.fromString(s)).setLastUseTime(new Date());
            return Response.ok(new DistanceMatrix(AppController.getSessionMap().get(
                    UUID.fromString(sessionId)).getStructure(), paramList, at1, at2),
                    MediaType.APPLICATION_JSON).build();
        });
    }

    /**
     *
     * @param sessionId mapped to pdb structure which is compute.
     * @return torsion angles matrix.
     */
    @GET
    @Path("angles")
    @Produces(MediaType.APPLICATION_JSON)
    public final List<String> getTorsionAngles(@QueryParam("sessionId") final String sessionId) {
        return new GreekAngleName().getGreekAngleNamesList();
    }

    /**
     * this method sends email with generated charts.
     */
    @GET
    @Path("sendEmail")
    @Produces(MediaType.TEXT_PLAIN)
    public final void sendMail() {
        Mail mail = new Mail("petr.ceranek@gmail.com", null);
        mail.sendMail("hw", "hello world!");
    }
}
