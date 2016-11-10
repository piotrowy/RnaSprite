package pl.poznan.put.Session;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.poznan.put.ConfigService;
import pl.poznan.put.StructureContainer;

import javax.inject.Inject;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

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

    @Scheduled(fixedRate = 600000)
    public void refreshSessionMap() {
        Date d = new Date();
        sessionMap.entrySet().stream().filter(map -> TimeUnit.MILLISECONDS.toMinutes(
                d.getTime() - map.getValue().getLastUseTime().getTime()) >= Integer.parseInt(
                this.configService.getSessionInterval())).forEach(map -> sessionMap.remove(map.getKey()));
    }

    public SessionData getSession(final UUID id) {
        return sessionMap.get(id);
    }

    public UUID createSession(final StructureContainer structure) {
        UUID id = UUID.randomUUID();
        sessionMap.put(id, new SessionData(structure, new Date()));
        return id;
    }
}
