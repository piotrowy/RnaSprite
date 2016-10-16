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
     * application email password.
     */
    @Getter
    private String emailPassword;

    /**
     * application name.
     */
    @Getter
    private String emailUsername;

    /**
     * application email host.
     */
    @Getter
    private String emailHost;

    /**
     * application email port.
     */
    @Getter
    private String emailPort;

    /**
     * application email address.
     */
    @Getter
    private String email;

    /**
     * time after which the session expires.
     */
    @Getter
    private String sessionInterval;

    /**
     * url to download all current pdb ids.
     */
    @Getter
    private String currentPdbIdsSetUrl;

    /**
     * url to download all obsolete pdb ids.
     */
    @Getter
    private String obsoletePdbIdsSetUrl;

    /**
     * time delay after which timerTask starts deleting session ids from sessionMap.
     */
    @Getter
    private String sessionMapDelay;

    /**
     * time after which sessionMap starts being updated.
     */
    @Getter
    private String sessionMapInterval;

    /**
     * time delay after which timerTask starts updating current and obsolete valid pdb ids.
     */
    @Getter
    private String pdbIdsSetDelay;

    /**
     * time after which pdb ids set starts being updated.
     */
    @Getter
    private String pdbIdsSetInterval;


    /**
     * it loads properties, from config.properties file, to properties variable.
     *
     * @throws IOException when config.properties file does not exist or it is not readable.
     */
    public ConfigService() throws IOException {
        this.properties = new Properties();
        this.properties.load(this.getClass().getClassLoader().getResourceAsStream("./config.properties"));

        this.emailPassword = this.properties.getProperty("mail.password");
        this.emailUsername = this.properties.getProperty("mail.username");
        this.emailHost = this.properties.getProperty("mail.host");
        this.email = this.properties.getProperty("mail.mail");
        this.emailPort = this.properties.getProperty("mail.port");

        this.sessionInterval = this.properties.getProperty("sessionHolder.sessionTime");

        this.obsoletePdbIdsSetUrl = this.properties.getProperty("obsoletePdbIds");
        this.currentPdbIdsSetUrl = this.properties.getProperty("currentPdbIds");

        this.sessionMapDelay = this.properties.getProperty("timerTask.sessionMap.timeDelay");
        this.sessionMapInterval = this.properties.getProperty("timerTask.sessionMap.timeInterval");
        this.pdbIdsSetDelay = this.properties.getProperty("timerTask.currentPdbIdList.timeDelay");
        this.pdbIdsSetInterval = this.properties.getProperty("timerTask.currentPdbIdList.timeInterval");
    }
}
