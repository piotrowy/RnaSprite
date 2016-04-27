package pl.put.poznan;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by piotrowy on 19.04.2016.
 */
public class SessionHolder {

    private static SessionHolder instance;
    private Map<UUID, SessionData> sessionMap;
    private Set<String> pdbSet;

    private SessionHolder(){
        this.sessionMap = new ConcurrentHashMap<>();
        this.pdbSet = StructureContainer.currentPdbIds();
        TimerTask timerTask1 = new TimerTask() {
            @Override
            public void run() {
                Date d = new Date();
                sessionMap.entrySet().stream().filter(map -> TimeUnit.MILLISECONDS.toMinutes(
                        d.getTime() - map.getValue().getLastUseTime().getTime()) >= 30).forEach(
                        map -> sessionMap.remove(map.getKey()));
            }
        };
        TimerTask timerTask2 = new TimerTask() {
            @Override
            public void run() {
                pdbSet = StructureContainer.currentPdbIds();
            }
        };
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(timerTask1, 0, 60000);
        timer.scheduleAtFixedRate(timerTask2, 0, 3600000);
    }

    public static SessionHolder takeInstance(){
        if (instance == null) {
            instance = new SessionHolder();
        }
        return SessionHolder.instance;
    }

    public Map<UUID, SessionData> getSessionMap() {
        return sessionMap;
    }

    public Set<String> getPdbSet() {
        return pdbSet;
    }

    public void setPdbSet(Set<String> pdbSet) {
        this.pdbSet = pdbSet;
    }
}
