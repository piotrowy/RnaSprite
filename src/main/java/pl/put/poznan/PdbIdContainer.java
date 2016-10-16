package pl.put.poznan;

import lombok.Getter;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by piotrowy on 15.10.2016.
 */
public final class PdbIdContainer {

    /**
     * pdbIdList is a list of all pdb ids (obsolete and current) downloaded from http://www.rcsb.org.
     */
    @Getter
    private static Set<String> pdbIdSet = allPdbIds();

    /**
     * Logger variable serves to log massages.
     */
    private static final Logger LOGGER = Logger.getLogger(StructureContainer.class);

    /**
     * private constructor...
     */
    private PdbIdContainer() { }

    /**
     *
     * @param pdbId - id of pdb file.
     * @return true if pdb id exists in pdbSet, in other case @return is false.
     */
    public static Boolean isPdbIdExists(final String pdbId) {
        return PdbIdContainer.pdbIdSet.contains(pdbId.toUpperCase());
    }

    /**
     *
     * @return all current, parsed pdb ids.
     */
    public static Set<String> currentPdbIds() {
        try {
            Set<String> pdbSet = new HashSet<>();
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(AppController.getConfig().getCurrentPdbIdsSetUrl());
            NodeList currentIds = doc.getElementsByTagName("PDB");
            for (int i = 0; i < currentIds.getLength(); i++) {
                pdbSet.add(currentIds.item(i).getAttributes().getNamedItem("structureId").getNodeValue());
            }
            return pdbSet;
        } catch (Exception ex) {
            LOGGER.error(ex);
        }
        return Collections.EMPTY_SET;
    }

    /**
     *
     * @return all obsolete, parsed pdb ids.
     */
    public static Set<String> obsoletePdbIds() {
        try {
            Set<String> pdbSet = new HashSet<>();
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(AppController.getConfig().getObsoletePdbIdsSetUrl());
            NodeList obsoleteIds = doc.getElementsByTagName("PDB");
            for (int i = 0; i < obsoleteIds.getLength(); i++) {
                pdbSet.add(obsoleteIds.item(i).getAttributes().getNamedItem("structureId").getNodeValue());
            }
            return pdbSet;
        } catch (Exception ex) {
            LOGGER.error(ex);
        }
        return Collections.EMPTY_SET;
    }

    /**
     *
     * @return all current and obsolete pdb ids (downloaded from http://www.rcsb.org).
     */
    public static Set<String> allPdbIds() {
        Set<String> newSet = new HashSet<String>(PdbIdContainer.currentPdbIds());
        newSet.addAll(PdbIdContainer.obsoletePdbIds());
        return newSet;
    }

    /**
     * it updates pdb ids set.
     */
    public static void update() {
        pdbIdSet = PdbIdContainer.allPdbIds();
    }

}
