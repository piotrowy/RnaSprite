package pl.poznan.put.rnacommons;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.inject.Named;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Named
public class PdbIdsManager {

    private static final String PDB_TAG = "PDB";
    private static final String ITEM_NAME = "structureId";
    private static final String CURRENT_URL = "http://www.rcsb.org/pdb/rest/getCurrent";
    private static final String OBSOLETE_URL = "http://www.rcsb.org/pdb/rest/getObsolete";

    /**
     * pdbIdList is a list of all pdb ids (obsolete and current) downloaded from http://www.rcsb.org.
     */
    @Getter
    private Set<String> pdbIdSet = allPdbIds();

    /**
     * @param uri where pdb sturctures are stored.
     * @return parsed pdb ids from uri.
     */
    private Set<String> getPdbIdsFromUri(final String uri) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(uri);

            NodeList currentIds = doc.getElementsByTagName(PDB_TAG);
            return IntStream.range(0, currentIds.getLength())
                    .mapToObj(idx -> currentIds.item(idx).getAttributes().getNamedItem(ITEM_NAME).getNodeValue())
                    .collect(Collectors.toSet());
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        return Collections.EMPTY_SET;
    }

    /**
     * @param pdbId - id of pdb file.
     * @return true if pdb id exists in pdbSet, in other case @return is false.
     */
    public final Boolean doesPdbIdExist(final String pdbId) {
        return pdbIdSet.contains(pdbId.toUpperCase());
    }

    /**
     * @return all current, parsed pdb ids.
     */
    public final Set<String> currentPdbIds() {
        return getPdbIdsFromUri(CURRENT_URL);
    }

    /**
     * @return all obsolete, parsed pdb ids.
     */
    public final Set<String> obsoletePdbIds() {
        return getPdbIdsFromUri(OBSOLETE_URL);
    }

    /**
     * @return all current and obsolete pdb ids (downloaded from http://www.rcsb.org).
     */
    public final Set<String> allPdbIds() {
        Set<String> newSet = new HashSet<String>(currentPdbIds());
        newSet.addAll(obsoletePdbIds());
        return newSet;
    }

    /**
     * it updates pdb ids set.
     */
    public final void update() {
        pdbIdSet = allPdbIds();
    }

    @Scheduled(fixedRate = 3600000)
    public final void refresh() {
        log.info("PDB IDS CRON");
        this.update();
    }

}
