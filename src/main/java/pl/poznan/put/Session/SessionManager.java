package pl.poznan.put.Session;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.poznan.put.Structure.PdbStructure;
import pl.poznan.put.Util.ConfigService;

import javax.inject.Inject;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public final class SessionManager {

    private final ConfigService configService;

    /**
     * sessionMap maps uuid to session data which is pdb structure
     * taking part in computation. Thanks to it users don't have to
     * upload structure each time when they want to compute something.
     */
    private static Map<UUID, SessionData> sessionMap = new ConcurrentHashMap<>();

    @Scheduled(fixedRate = 300000)
    public void refreshSessionMap() {
        log.info("SESSION CRON");
        sessionMap.entrySet().stream()
                .filter(map -> TimeUnit.MILLISECONDS.toMinutes(new Date().getTime() - map.getValue().getLastUseTime().getTime())
                        >= Integer.parseInt(this.configService.getSessionInterval()))
                .forEach(map -> sessionMap.remove(map.getKey()));
    }

    public SessionData getSession(final UUID id) {
        SessionData sessionData = sessionMap.get(id);
        sessionData.setLastUseTime(new Date());
        return sessionData;
    }

    public Boolean hasSession(final UUID id) {
        return sessionMap.containsKey(id);
    }

    public UUID createSession(final PdbStructure structure) {
        UUID id = UUID.randomUUID();
        sessionMap.put(id, SessionData.builder().structure(structure).lastUseTime(new Date()).build());
        return id;
    }

    public static Map<UUID, SessionData> getSessionMap() {
        return SessionManager.sessionMap;
    }
}
