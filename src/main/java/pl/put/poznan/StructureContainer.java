/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.put.poznan;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import pl.poznan.put.constant.Unicode;
import pl.poznan.put.pdb.PdbParsingException;
import pl.poznan.put.pdb.analysis.PdbModel;
import pl.poznan.put.structure.tertiary.StructureManager;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.util.*;

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
    private static Map<String, String> greekAnglesNames = fillGreekAnglesNames();
    private static final String USER_AGENT = "Mozilla/5.0";


    final private static Logger logger = Logger.getLogger(StructureContainer.class);

    /**
     *
     * @param pdbId - name of pdb model
     */
    public StructureContainer(String pdbId) {
        try {
            structureList = StructureManager.loadStructure(pdbId);
        } catch (Exception ex) {
            structureList = Collections.EMPTY_LIST;
        }
    }

    /**
     *
     * @param pdbFile - file or archive with model/s
     */
    public StructureContainer(File pdbFile) throws IOException, PdbParsingException {
            structureList = StructureManager.loadStructure(pdbFile);
    }

    public static Boolean isPdbIdExists(String pdbId, Set<String> pdbSet) {
        return pdbSet.contains(pdbId.toUpperCase());
    }

    public static Set<String> currentPdbIds() {
        try {
            Set<String> pdbSet = new HashSet<>();
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse("http://www.rcsb.org/pdb/rest/getCurrent");
            NodeList currentIds = doc.getElementsByTagName("PDB");
            for (int i = 0; i < currentIds.getLength(); i++) {
                pdbSet.add(currentIds.item(i).getAttributes().getNamedItem("structureId").getNodeValue());
            }
            return pdbSet;
        } catch (Exception ex) {
            logger.error(ex);
        }
        return Collections.EMPTY_SET;
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

}
