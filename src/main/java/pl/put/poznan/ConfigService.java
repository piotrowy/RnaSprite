package pl.put.poznan;

import lombok.Getter;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by piotrowy on 04.05.2016.
 */
public final class ConfigService {

    /**
     * properties variable which stores properties from config.properties file.
     */
    @Getter
    private Properties properties;

    /**
     * it loads properties, from config.properties file, to properties variable.
     * @throws IOException when config.properties file does not exist or it is not readable.
     */
    public ConfigService() throws IOException {
        this.properties = new Properties();
        this.properties.load(this.getClass().getClassLoader().getResourceAsStream("./config.properties"));
    }

    /**
     *
     * @return application email password.
     */
    public String emailPassword() {
        return this.properties.getProperty("mail.password");
    }

    /**
     *
     * @return application name.
     */
    public String emailUsername() {
        return this.properties.getProperty("mail.username");
    }

    /**
     *
     * @return application email host.
     */
    public String emailHost() {
        return this.properties.getProperty("mail.host");
    }

    /**
     *
     * @return application email address.
     */
    public String email() {
        return this.properties.getProperty("mail.mail");
    }

    /**
     *
     * @return application email port.
     */
    public String emailPort() {
        return this.properties.getProperty("mail.port");
    }

    /**
     *
     * @return time delay after which timerTask starts deleting session ids from sessionMap.
     */
    public String sessionMapDelay() {
        return this.properties.getProperty("timerTask.sessionMap.timeDelay");
    }

    /**
     *
     * @return time after which sessionMap is updated.
     */
    public String sessionMapInterval() {
        return this.properties.getProperty("timerTask.sessionMap.timeInterval");
    }

    /**
     *
     * @return time delay after which timerTask starts updating current and obsolete valid pdb ids.
     */
    public String pdbIdsSetDelay() {
        return this.properties.getProperty("timerTask.currentPdbIdList.timeDelay");
    }

    /**
     *
     * @return time after which pdb ids set is updated.
     */
    public String pdbIdsSetInterval() {
        return this.properties.getProperty("timerTask.currentPdbIdList.timeInterval");
    }

    /**
     *
     * @return url to download all current pdb ids.
     */
    public String currentPdbListUrl() {
        return this.properties.getProperty("currentPdbIds");
    }

    /**
     *
     * @return url to download all obsolete pdb ids.
     */
    public String obsoletePdbListUrl() {
        return this.properties.getProperty("obsoletePdbIds");
    }

    /**
     *
     * @return time after which the session expires.
     */
    public String sessionInterval() {
        return this.properties.getProperty("sessionHolder.sessionTime");
    }
}
