/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.put.poznan;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import java.util.List;
import java.util.Map;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import pl.poznan.put.constant.Unicode;

import pl.poznan.put.pdb.PdbParsingException;
import pl.poznan.put.pdb.analysis.PdbModel;
import pl.poznan.put.structure.tertiary.StructureManager;

/**
 *
 * @author piotrowy
 */
public class StructureContainer {

    /**
     * @return the greekAnglesNames
     */
    public static Map<String, String> getGreekAnglesNames() {
        return greekAnglesNames;
    }

    private List<PdbModel> structureList;
    private Response respIfNull;
    private static Map<String, String> greekAnglesNames = fillGreekAnglesNames();

    final private static Logger logger = Logger.getLogger(StructureContainer.class);

    /**
     *
     * @param pdbId - name of pdb model
     */
    public StructureContainer(String pdbId) {
        try {
            structureList = StructureManager.loadStructure(pdbId);
            respIfNull = null;
        } catch (IOException | PdbParsingException ex) {
            String failureMessage = "Nie znaleziono podanej nazwy w bazie.";
            respIfNull = Response.status(Response.Status.BAD_REQUEST).entity(failureMessage + '\n' + ex).build();
            logger.warn(ex);
        }
    }

    /**
     *
     * @param pdbFile - file or archive with model/s
     */
    public StructureContainer(File pdbFile) {
        try {
            structureList = StructureManager.loadStructure(pdbFile);
            respIfNull = null;
        } catch (IOException | PdbParsingException ex) {
            String failureMessage = "Nieprawidłowy plik PDB.";
            respIfNull = Response.status(Response.Status.BAD_REQUEST).entity(failureMessage + '\n' + ex).build();
            logger.warn(ex);
        }
    }

    private static Map<String, String> fillGreekAnglesNames() {
        Map<String, String> map = new HashMap<>();
        map.put("alpha", Unicode.ALPHA);
        map.put("beta", Unicode.BETA);
        map.put("gamma", Unicode.GAMMA);
        map.put("delta", Unicode.DELTA);
        map.put("epsilon", Unicode.EPSILON);
        map.put("zeta", Unicode.ZETA);
        map.put("nu0", Unicode.NU0);
        map.put("nu1", Unicode.NU1);
        map.put("nu2", Unicode.NU2);
        map.put("nu3", Unicode.NU3);
        map.put("nu4", Unicode.NU4);
        map.put("chi", Unicode.CHI);
        map.put("eta", Unicode.ETA);
        map.put("theta", Unicode.THETA);
        map.put("eta_prim", Unicode.ETA_PRIM);
        map.put("theta_prim", Unicode.THETA_PRIM);
        map.put("pseudophase_pucker", "P");
        return map;
    }

    /**
     * @return the respIfNull
     */
    public Response getRespIfNull() {
        return respIfNull;
    }

    /**
     * @return the structureList
     */
    public List<PdbModel> getStructureList() {
        return structureList;
    }

    /**
     * @param structureList the structureList to set
     */
    public void setStructureList(List<PdbModel> structureList) {
        this.structureList = structureList;
    }

    /**
     * @param respIfNull the respIfNull to set
     */
    public void setRespIfNull(Response respIfNull) {
        this.respIfNull = respIfNull;
    }
}
