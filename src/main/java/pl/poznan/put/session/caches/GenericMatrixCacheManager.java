package pl.poznan.put.session.caches;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import pl.poznan.put.rnamatrix.model.Matrix;
import pl.poznan.put.rnamatrix.model.MtxStructure;
import pl.poznan.put.session.SessionManager;

import java.util.Date;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class GenericMatrixCacheManager<T extends Matrix, U> {

    private static final String SESSION_TIME = "sessionCacheManager.sessionTime";
    private static final String CHECK_SESSIONS_INTERVAL = "timerTask.sessions.timeInterval";
    private static final Integer DELAY = 10;
    private final Environment env;
    private final StructureCacheManager structureCacheManager;
    private final SessionManager sessionManager;
    private Map<UUID, MatrixCache<T, U>> matrixCacheMap = new ConcurrentHashMap<>();

    @PostConstruct
    public void setTimer() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                refreshMatrixCacheMap();
            }
        };
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(timerTask, TimeUnit.MINUTES.toMillis(DELAY),
                TimeUnit.MINUTES.toMillis(env.getProperty(CHECK_SESSIONS_INTERVAL, Integer.class)));
    }

    private void refreshMatrixCacheMap() {
        log.info("SESSION CRON - START");
        matrixCacheMap.entrySet()
                .stream()
                .filter(map -> TimeUnit.MILLISECONDS.toMinutes(new Date().getTime() - map.getValue().getLastUseTime().getTime())
                        >= Integer.parseInt(env.getProperty(SESSION_TIME)))
                .forEach(map -> {
                    log.debug("Removed session id: {}", map.getKey());
                    matrixCacheMap.remove(map.getKey());
                });
        structureCacheManager.getKeys().stream().filter(sessionManager::sessionExistForStructure).forEach(structureCacheManager::remove);
        log.info("SESSION CRON - END");
    }

    public MatrixCache<T, U> getMatrixCache(final UUID id) {
        if (matrixCacheMap.containsKey(id)) {
            MatrixCache<T, U> cache = matrixCacheMap.get(id);
            cache.setLastUseTime(new Date());
            return cache;
        }
        return null;
    }

    public UUID createMatrixCache(final MtxStructure<T, U> structure) {
        UUID id = UUID.randomUUID();
        return createMatrixCache(structure, id);
    }

    public UUID createMatrixCache(final MtxStructure<T, U> structure, final UUID id) {
        matrixCacheMap.put(id, MatrixCache.<T, U>builder()
                .matrix(structure)
                .lastUseTime(new Date())
                .build());
        return id;
    }

    public void refreshMatrixCache(final UUID id) {
        if (getMatrixCache(id) != null) {
            getMatrixCache(id).setLastUseTime(new Date());
        }
    }

    public boolean exists(final UUID id) {
        return matrixCacheMap.keySet().contains(id);
    }
}
