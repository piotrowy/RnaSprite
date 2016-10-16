package pl.put.poznan;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by piotrowy on 04.05.2016.
 */
public class ConfigService {

    private static Properties properties;

    public ConfigService() throws IOException {
        properties = new Properties();
        properties.load(getClass().getClassLoader().getResourceAsStream("./config.properties"));
    }

    public String emailPassword() {
        return properties.getProperty("mail.password");
    }

    public String emailUsername() {
        return properties.getProperty("mail.username");
    }

    public String emailHost() {
        return properties.getProperty("mail.host");
    }

    public String email() {
        return properties.getProperty("mail.mail");
    }

    public String emailPort() {
        return properties.getProperty("mail.port");
    }

    public String sessionMapDelay() {
        return properties.getProperty("timertask.sessionMap.timeDelay");
    }

    public String sessionMapInterval() {
        return properties.getProperty("timertask.sessionMap.timeInterval");
    }

    public String pdbIdListDelay() {
        return properties.getProperty("timertask.currentPdbIdList.timeDelay");
    }

    public String pdbIdListInterval() {
        return properties.getProperty("timertask.currentPdbIdList.timeInterval");
    }

    public String currentPdbListUrl(){
        return properties.getProperty("currentPdbIds");
    }

    public String obsoletePdbListUrl(){
        return properties.getProperty("obsoletePdbIds");
    }

    public String sessionInterval() { return properties.getProperty("sessionHolder.sessionTime"); }

}
