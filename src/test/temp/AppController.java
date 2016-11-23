package pl.poznan.put;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import pl.poznan.put.rnacommons.GreekAngleName;
import pl.poznan.put.session.SessionData;
import pl.poznan.put.util.ConfigService;

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
@Slf4j
public class AppController {


    /**
     * @param uploadedInputStream pdb file uploaded by user.
     * @param fileDetail          file details?
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
            log.warn(ex.getMessage());
        } finally {
            FileUtils.deleteQuietly(uploadedFileLocation);
            IOUtils.closeQuietly(outputStream);
        }
        return Response.status(Response.Status.NOT_FOUND).entity(FAILURE_MESSAGE).build();
    }

    /**
     * @param sessionId which is mapped to pdb structure which is compute.
     * @param chain     in which application finds residues.
     * @param at1       distance is counted betwee two atoms.
     * @param at2       distance is counted betwee two atoms.
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
     * @param sessionId           which is mapped to pdb structure which is compute.
     * @param fragmentsDefinition ???
     * @param paramList           ???
     * @param at1                 distance is counted betwee two atoms.
     * @param at2                 distance is counted betwee two atoms.
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
}
