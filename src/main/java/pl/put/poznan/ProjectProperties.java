package pl.put.poznan;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by piotrowy on 04.05.2016.
 */
public class ProjectProperties {

    private Properties properties;

    public ProjectProperties() {
        this.properties = new Properties();
        try {
            properties.load(getClass().getResourceAsStream("/config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Properties getProperties() {
        return properties;
    }
}
