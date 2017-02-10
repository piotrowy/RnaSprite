package pl.poznan.put.core.session.caches;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.core.env.Environment;
import pl.poznan.put.core.structure.PdbStructure;
import pl.poznan.put.tables.daos.PdbIdSessionIdDao;
import pl.poznan.put.exceptions.InvalidArgumentException;

import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import static pl.poznan.put.tables.PdbIdSessionId.PDB_ID_SESSION_ID;

@Slf4j
@Named
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class StructureCacheManager {

    private static final String SESSION_INTERVAL = "structureCacheManager.structureTime";
    private static final String CHECK_STRUCTURES_INTERVAL = "timerTask.structures.timeInterval";
    private static final Integer DELAY = 10;

    private static Map<String, PdbStructure> structureCacheMap = new ConcurrentHashMap<>();

    private final Environment env;
    private final PdbIdSessionIdDao pdbIdSessionIdDao;
    private final DSLContext jooq;

    @PostConstruct
    public void setTimer() {
        jooq.delete(PDB_ID_SESSION_ID).execute();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                refreshSessionMap();
            }
        };
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(timerTask, TimeUnit.MINUTES.toMillis(DELAY),
                TimeUnit.MINUTES.toMillis(env.getProperty(CHECK_STRUCTURES_INTERVAL, Integer.class)));
    }

    private void refreshSessionMap() {
        log.info("STRUCTURES CRON - START");
        structureCacheMap.keySet().stream().filter(pdbId -> pdbIdSessionIdDao.fetchByPdbId(pdbId).isEmpty()).forEach(this::remove);
        log.info("STRUCTURES CRON - END");
    }

    public PdbStructure getStructure(String pdbId) throws EmptyCacheException {
        if (structureCacheMap.containsKey(pdbId)) {
            return structureCacheMap.get(pdbId);
        }
        throw new EmptyCacheException("Unable to find structure.");
    }

    public void createStructureRecord(String pdbId) throws InvalidArgumentException {
        try {
            if (!structureCacheMap.keySet().contains(pdbId)) {
                structureCacheMap.put(pdbId, PdbStructure.fromString(pdbId));
            }
        } catch (Exception ex) {
            log.error("Could not create structure. {}", ex);
            throw new InvalidArgumentException(ex);
        }
    }

    Set<String> getKeys() {
        return structureCacheMap.keySet();
    }

    void remove(String pdbId) {
        structureCacheMap.remove(pdbId);
    }
}
