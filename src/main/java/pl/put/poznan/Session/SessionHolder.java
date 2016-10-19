package pl.put.poznan.Session;

import lombok.Getter;
import pl.put.poznan.AppController;
import pl.put.poznan.PdbIdContainer;

import java.util.Date;
import java.util.Map;
import java.util.TimerTask;
import java.util.Timer;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by piotrowy on 19.04.2016.
 */
public final class SessionHolder {

    /**
     * instance of SessionHolder class. It can be created only once.
     */
    private static SessionHolder instance;

    /**
     * sessionMap maps uuid to session data which is pdb structure
     * taking part in computation. Thanks to it users don't have to
     * upload structure each time when they want to compute something.
     */
    @Getter
    private Map<UUID, SessionData> sessionMap;

    /**
     * SessionHolde class constructor. It is called only once per
     * lifecycle of application.
     *
     * It initializes sessionMap which store data for users. It create
     * two timerTasks. First one updates sessionMap, every 10 minutes,
     * in order to each key wes in map only for 30 minutes. Second one
     * update pdb ids set, every hour, in order to have always correct
     * pdb ids.
     */
    private SessionHolder() {
        this.sessionMap = new ConcurrentHashMap<>();
        TimerTask timerTask1 = new TimerTask() {
            @Override
            public void run() {
                Date d = new Date();
                sessionMap.entrySet().stream().filter(map -> TimeUnit.MILLISECONDS.toMinutes(
                        d.getTime() - map.getValue().getLastUseTime().getTime()) >= Integer.parseInt(
                        AppController.getConfig().getSessionInterval())).forEach(map -> sessionMap.remove(map.getKey()));
            }
        };
        TimerTask timerTask2 = new TimerTask() {
            @Override
            public void run() {
                PdbIdContainer.update();
            }
        };
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(timerTask1,
                Integer.parseInt(AppController.getConfig().getSessionMapDelay()),
                Integer.parseInt(AppController.getConfig().getSessionMapInterval()));
        timer.scheduleAtFixedRate(timerTask2,
                Integer.parseInt(AppController.getConfig().getPdbIdsSetDelay()),
                Integer.parseInt(AppController.getConfig().getPdbIdsSetInterval()));
    }

    /**
     *
     * @return the only instance of SessionHolder class. SessionHolder
     * class is a singleton.
     */
    public static SessionHolder getInstance() {
        if (instance == null) {
            instance = new SessionHolder();
        }
        return SessionHolder.instance;
    }
}
