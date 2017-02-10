package pl.poznan.put.core.csv;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import pl.poznan.put.util.FileUtils;

import java.io.File;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

@Slf4j
@Named
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class CsvEraser {

    private static final String CHECK_CSV_INTERVAL = "timerTask.csvLifeCycle.timeInterval";
    private static final Integer DELAY = 2;
    private final Environment env;
    private Map<File, Boolean> csvCache = new ConcurrentHashMap<>();

    @PostConstruct
    public void setTimer() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                log.info("CSV ERASER – START");
                eraseCsvMatrices();
                log.info("CSV ERASER – END");
            }
        };
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(timerTask, TimeUnit.MINUTES.toMillis(DELAY),
                TimeUnit.MINUTES.toMillis(env.getProperty(CHECK_CSV_INTERVAL, Integer.class)));
    }

    private void eraseCsvMatrices() {
        Set<File> keySet = csvCache.keySet();
        keySet.forEach(file -> {
            if (csvCache.get(file)) {
                FileUtils.delete(file);
                csvCache.remove(file);
            } else {
                csvCache.put(file, true);
            }
        });
    }

    void add(File file) {
        csvCache.put(file, false);
    }
}
