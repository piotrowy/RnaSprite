/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.put.poznan;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

/**
 *
 * @author piotrowy
 */
@Path("/options")
public class AppController {

	private static String emailContent;

	@GET
	@Path("downloadTorsionAnglesFromPdb")
	@Produces(MediaType.APPLICATION_JSON)
	public TorsionAngleMatrix getAnglesFromPDB(
			@QueryParam("structurePDB") String structurePDB) {
		StructureContainer container = new StructureContainer(structurePDB);
		TorsionAngleMatrix mtx = new TorsionAngleMatrix(container);
		return mtx;
	}

	@GET
	@Path("downloadDistanceMatrixFromPdb")
	@Produces(MediaType.APPLICATION_JSON)
	public DistanceMatrix getDistanceMatrixFromPDB(
			@QueryParam("structurePDB") String structurePDB,
			@QueryParam("chain") String chain, @QueryParam("at1") String at1,
			@QueryParam("at2") String at2) {
		StructureContainer container = new StructureContainer(structurePDB);
		DistanceMatrix mtx = new DistanceMatrix(container, chain, at1, at2);
		return mtx;
	}

	@GET
	@Path("getAtomsList")
	@Produces(MediaType.APPLICATION_JSON)
	public AtomNamesList getAnglesList() {
		AtomNamesList atomsList = new AtomNamesList();
		return atomsList;
	}

	@GET
	@Path("getChainsList")
	@Produces(MediaType.APPLICATION_JSON)
	public ChainsIdList getChainsList(
			@QueryParam("structurePDB") String structurePDB) {
		StructureContainer container = new StructureContainer(structurePDB);
		ChainsIdList chainsList = new ChainsIdList(container);
		return chainsList;
	}

	@POST
	@Path("/downloadTorsionAnglesFromFile")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.TEXT_HTML)
	public Response getAnglesFromFile(
			@FormDataParam("modelFile") InputStream uploadedInputStream,
			@FormDataParam("modelFile") FormDataContentDisposition fileDetail)
			throws IOException {

		File uploadedFileLocation = File.createTempFile("RNAsprite", ".pdb");

		// writeToFile(uploadedInputStream, uploadedFileLocation);
		FileOutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(uploadedFileLocation);
			IOUtils.copy(uploadedInputStream, outputStream);
			String output = "File uploaded to : " + uploadedFileLocation;
			System.out.println(output);
			StructureContainer structureModel = new StructureContainer(
					uploadedFileLocation);
			return Response.status(200).entity(output).build();
		} catch (Exception ex) {
			// response error
		} finally {
			uploadedFileLocation.delete();
			IOUtils.closeQuietly(outputStream);
		}
		return null;
	}

	@GET
	@Path("sendEmail")
	@Produces(MediaType.TEXT_PLAIN)
	public Response sendEmail(@QueryParam("sentTo") String sentTo) {

		return null;
	}
}
