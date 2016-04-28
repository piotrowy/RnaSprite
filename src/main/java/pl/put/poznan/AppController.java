/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.put.poznan;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Predicate;


/**
 *
 * @author piotrowy
 */
@Path("/options")
public class AppController {

	final static Logger logger = Logger.getLogger(AppController.class);
	private Map<UUID, SessionData> sessionMap = SessionHolder.takeInstance().getSessionMap();
	private static String FAILURE_MESSAGE = "Entity not found for UUID: ";
	private static String SUCCSES_MESSAGE = "SUCCSES";

    private String generateSessionData(StructureContainer structure){
        UUID id = UUID.randomUUID();
        this.sessionMap.put(id, new SessionData(structure, new Date()));
        return id.toString();
    }

    private Response checkSessionIdAndGetResponse(String sessionId, Function<String, Response> func){
        return getResponse(sessionId, (s) -> !s.equals("") &&
                this.sessionMap.containsKey(UUID.fromString(s.toString())), func);
    }

    private Response checkPdbIdAndGetResponse(String pdbId, Function<String, Response> func) {
        return getResponse(pdbId, (s) -> !s.equals("") && s.toString().length() == 4 && StructureContainer.
                isPdbIdExists((String)s, SessionHolder.takeInstance().getPdbSet()) == true, func);
    }

    private Response getResponse(String param, Predicate pred, Function<String, Response> func) {
        if (pred.test(param)){
            return func.apply(param);
        }
        return Response.status(Response.Status.NOT_FOUND).entity(FAILURE_MESSAGE + param).build();
    }

	@GET
	@Path("upload-structure-pdbid")
	@Produces(MediaType.APPLICATION_JSON)
	public Response uploadStructure (@QueryParam("structurePDB") String structurePDB) {
        return checkPdbIdAndGetResponse(structurePDB.toUpperCase(), (s) -> Response.ok(generateSessionData(new StructureContainer(s)),
                MediaType.APPLICATION_JSON).build());
    }

	@POST
	@Path("upload-structure-file")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.TEXT_HTML)
	public Response uploadStructure(@FormDataParam("modelFile") InputStream uploadedInputStream,
                                      @FormDataParam("modelFile") FormDataContentDisposition fileDetail) {
        File uploadedFileLocation = null;
        FileOutputStream outputStream = null;

        try {
            uploadedFileLocation = File.createTempFile("RNAsprite", ".pdb");
            outputStream = new FileOutputStream(uploadedFileLocation);
            IOUtils.copy(uploadedInputStream, outputStream);
            String output = "File uploaded to : " + uploadedFileLocation;
            logger.info(output);
            String id = generateSessionData(new StructureContainer(
                    uploadedFileLocation));
            return Response.status(200).entity(id).type("Application/json").build();
        } catch (Exception  ex) {
            logger.warn(ex);
        } finally {
            FileUtils.deleteQuietly(uploadedFileLocation);
            IOUtils.closeQuietly(outputStream);
        }
        return Response.status(404).entity(FAILURE_MESSAGE).type("Application/json").build();
	}

	@GET
	@Path("atoms-list")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAnglesList() {
        return Response.ok(new AtomNamesList(), MediaType.APPLICATION_JSON).build();
    }


	@GET
	@Path("chain-list")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getChainsList(
			@QueryParam("sessionId") String sessionId) {
        return checkSessionIdAndGetResponse(sessionId, (s) -> {
            this.sessionMap.get(UUID.fromString(s)).setLastUseTime(new Date());
            return Response.ok(new ChainsIdList(this.sessionMap.get(UUID.fromString(s)).getStructure()),
                    MediaType.APPLICATION_JSON).build();
        });
	}

	@GET
	@Path("distance-matrix")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDistanceMatrix(
			@QueryParam("sessionId") String sessionId,
			@QueryParam("chain") String chain,
			@QueryParam("at1") String at1,
			@QueryParam("at2") String at2) {
        return checkSessionIdAndGetResponse(sessionId, (s) -> {
            this.sessionMap.get(UUID.fromString(s)).setLastUseTime(new Date());
            return Response.ok(new DistanceMatrix(this.sessionMap.get(UUID.fromString(sessionId)).getStructure(),
                    chain, at1, at2), MediaType.APPLICATION_JSON).build();
        });
	}

	@GET
	@Path("distance-matrix-fg")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getFragmentOfDistanceMatrix(
			@QueryParam("sessionId") String sessionId,
			@QueryParam("fragmentsDefinition") List<String> fragmentsDefinition,
			@QueryParam("paramList") List<String> paramList,
			@QueryParam("at1") String at1,
			@QueryParam("at2") String at2) {
        return checkSessionIdAndGetResponse(sessionId, (s) -> {
            this.sessionMap.get(UUID.fromString(s)).setLastUseTime(new Date());
            return Response.ok(new DistanceMatrix(this.sessionMap.get(UUID.fromString(sessionId)).getStructure(),
                    paramList, at1, at2), MediaType.APPLICATION_JSON).build();
        });
	}

	@GET
	@Path("torsion-angles")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTorsionAngles(@QueryParam("sessionId") String sessionId) {
        return checkSessionIdAndGetResponse(sessionId, (s) -> {
            this.sessionMap.get(UUID.fromString(s)).setLastUseTime(new Date());
            return Response.ok(new TorsionAngleMatrix(this.sessionMap.get(UUID.fromString(s)).getStructure()),
                    MediaType.APPLICATION_JSON).build();
        });
	}

	@GET
	@Path("send-mail")
    @Produces(MediaType.TEXT_PLAIN)
	public void sendMail() {
        Mail mail = new Mail("petr.ceranek@gmail.com", null);
        mail.sendMail("hw", "hello world!");
    }
    
    // TODO: 22.03.2016 - metody do plikow
    // TODO: 22.03.2016 -  wypluwanie csv
    // TODO: 22.03.2016 - metoda do pliku konfiguracyjnego
	// TODO: 31.03.2016 - forna pozytac d3js.org
	// TODO: 31.03.2016 - sesja, nie ladowac 10x tego samego
	// TODO: 31.03.2016 - metoda distancematrix ze szczegolwym definiowaniem atomow A63A,5;[...] - dla katow tez, ktore katy
	// TODO: 31.03.2016 - jsmol wyswietlanie struktury
	// TODO: 31.03.2016 - svg plik do wysylania na mejla
	// TODO: 31.03.2016 - statyczna mapa do taskow + listener do czyszczenia
}
