package pl.poznan.put;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public final class Application {

    private Application() { }

    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
