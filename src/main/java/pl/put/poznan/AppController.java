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
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.TimeUnit;


/**
 *
 * @author piotrowy
 */
@Path("/options")
public class AppController {

	final static Logger logger = Logger.getLogger(AppController.class);
	private Map<UUID, SessionData> sessionMap;

	public AppController () {
		this.sessionMap = new HashMap<>();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Date d = new Date();
                sessionMap.entrySet().stream().filter(map -> TimeUnit.MILLISECONDS.toMinutes(
                        d.getTime() - map.getValue().getLastUseTime().getTime()) <= 30).forEach(
                        map -> sessionMap.remove(map.getKey()));
            }
        };
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(timerTask, 0, 60000);
	}

	@GET
	@Path("upload-structure-pdbid")
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject uploadStructure (@QueryParam("structurePDB") String structurePDB) {
		String id = generateSessionData(new StructureContainer(structurePDB));
		JSONObject resultJSON = new JSONObject();
        resultJSON.put("id", id);
		return resultJSON;
	}

	@POST
	@Path("upload-structure-file")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.TEXT_HTML)
	public JSONObject uploadStructure(@FormDataParam("modelFile") InputStream uploadedInputStream,
                                      @FormDataParam("modelFile") FormDataContentDisposition fileDetail) {
        File uploadedFileLocation = null;
        FileOutputStream outputStream = null;
        String failMessage = "Server failed: ";
        JSONObject resultJSON = new JSONObject();

        try {
            uploadedFileLocation = File.createTempFile("RNAsprite", ".pdb");
            outputStream = new FileOutputStream(uploadedFileLocation);
            IOUtils.copy(uploadedInputStream, outputStream);
            String output = "File uploaded to : " + uploadedFileLocation;
            logger.info(output);
            String id = generateSessionData(new StructureContainer(
                    uploadedFileLocation));
            resultJSON.put("id", id);
            return resultJSON;
        } catch (IOException  ex) {
            logger.warn(ex);
            failMessage += ex;
        } finally {
            FileUtils.deleteQuietly(uploadedFileLocation);
            IOUtils.closeQuietly(outputStream);
        }
        resultJSON.put("Message", failMessage);
        return resultJSON;
	}

	private String generateSessionData(StructureContainer structure){
		String id = UUID.randomUUID().toString();
		this.sessionMap.put(UUID.fromString(id), new SessionData(structure, new Date()));
		return id;
	}

	@GET
	@Path("atoms-list")
	@Produces(MediaType.APPLICATION_JSON)
	public AtomNamesList getAnglesList() {
		return new AtomNamesList();
	}

	@GET
	@Path("chain-list")
	@Produces(MediaType.APPLICATION_JSON)
	public ChainsIdList getChainsList(
			@QueryParam("sessionId") String sessionId) {
        this.sessionMap.get(UUID.fromString(sessionId)).setLastUseTime(new Date());
		return new ChainsIdList(this.sessionMap.get(UUID.fromString(sessionId)).getStructure());
	}

	@GET
	@Path("distance-matrix")
	@Produces(MediaType.APPLICATION_JSON)
	public DistanceMatrix getDistanceMatrix(
			@QueryParam("sessionId") String sessionId,
			@QueryParam("chain") String chain,
			@QueryParam("at1") String at1,
			@QueryParam("at2") String at2) {
        this.sessionMap.get(UUID.fromString(sessionId)).setLastUseTime(new Date());
		return new DistanceMatrix(this.sessionMap.get(UUID.fromString(sessionId)).getStructure(), chain, at1, at2);
	}

	@GET
	@Path("distance-matrix-fg")
	@Produces(MediaType.APPLICATION_JSON)
	public DistanceMatrix getFragmentOfDistanceMatrix(
			@QueryParam("sessionId") String sessionId,
			@QueryParam("fragmentsDefinition") List<String> fragmentsDefinition,
			@QueryParam("paramList") List<String> paramList,
			@QueryParam("at1") String at1,
			@QueryParam("at2") String at2) {
        this.sessionMap.get(UUID.fromString(sessionId)).setLastUseTime(new Date());
        return new DistanceMatrix(this.sessionMap.get(UUID.fromString(sessionId)).getStructure(),
                paramList, at1, at2);
	}

	@GET
	@Path("downloadTorsionAngles")
	@Produces(MediaType.APPLICATION_JSON)
	public TorsionAngleMatrix getAnglesFromPDB(
			@QueryParam("sessionId") String sessionId) {
        this.sessionMap.get(UUID.fromString(sessionId)).setLastUseTime(new Date());
		return new TorsionAngleMatrix(this.sessionMap.get(UUID.fromString(sessionId)).getStructure());
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
