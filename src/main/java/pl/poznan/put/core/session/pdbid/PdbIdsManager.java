package pl.poznan.put.core.session.pdbid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.dao.DuplicateKeyException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import pl.poznan.put.tables.daos.PdbIdsDao;
import pl.poznan.put.tables.pojos.PdbIds;

import java.util.Collections;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

@Slf4j
@Named
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class PdbIdsManager {

    private static final String PDB_TAG = "PDB";
    private static final String ITEM_NAME = "structureId";
    private static final String INTERVAL_IN_HOURS = "timerTask.pdbIds.timeInterval";
    private static final String CURRENT_URL = "http://www.rcsb.org/pdb/rest/getCurrent";
    private static final String OBSOLETE_URL = "http://www.rcsb.org/pdb/rest/getObsolete";
    private static final Integer DELAY = 0;

    private final PdbIdsDao pdbIdsDao;
    private final Environment env;

    @PostConstruct
    public void setTimer() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                updatePdbIds();
            }
        };
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(timerTask, TimeUnit.HOURS.toMillis(DELAY),
                TimeUnit.HOURS.toMillis(env.getProperty(INTERVAL_IN_HOURS, Integer.class)));
    }

    boolean pdbIdExists(final String pdbId) {
        return pdbIdsDao.fetchOneByName(pdbId) != null;
    }

    private void updatePdbIds() {
        log.info("PDB IDS CRON - START");
        Set<PdbIds> pdbIds = currentPdbIds().stream()
                .map(pdbId -> new PdbIds(null, pdbId, false))
                .collect(Collectors.toSet());

        pdbIds.addAll(obsoletePdbIds().stream()
                .map(pdbId -> new PdbIds(null, pdbId, true))
                .collect(Collectors.toSet()));

        if (pdbIdsDao.count() == 0) {
            pdbIdsDao.insert(pdbIds);
        } else {
            pdbIds.forEach(pdbId -> {
                try {
                    pdbIdsDao.insert(pdbId);
                } catch (DuplicateKeyException ex) {
                    log.debug("Pdb key already exists. {}, {}", pdbId, ex);
                }
            });
        }
        log.info("PDB IDS CRON - END");
    }

    private Set<String> getPdbIdsFromUri(final String uri) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(uri);

            NodeList currentIds = doc.getElementsByTagName(PDB_TAG);
            return IntStream.range(0, currentIds.getLength())
                    .mapToObj(idx -> currentIds.item(idx).getAttributes().getNamedItem(ITEM_NAME).getNodeValue())
                    .collect(Collectors.toSet());
        } catch (Exception ex) {
            log.error("Downloading pdb ids failed. {}", ex);
        }
        return Collections.emptySet();
    }

    private Set<String> currentPdbIds() {
        return getPdbIdsFromUri(CURRENT_URL);
    }

    private Set<String> obsoletePdbIds() {
        return getPdbIdsFromUri(OBSOLETE_URL);
    }
}
