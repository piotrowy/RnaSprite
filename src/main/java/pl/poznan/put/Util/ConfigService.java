package pl.poznan.put.Util;

import lombok.Getter;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.Properties;

@Configuration
public class ConfigService {

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
    private String emailName;

    @Getter
    private String smtpAuth;

    @Getter
    private String smtpStarttls;

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

    @Getter
    private String errorMessage;

    @Getter
    private String exceptionNotFoundMessage;

    @Getter
    private String exceptionInvalidEmailAddressMessage;

    @Getter
    private String exceptionStructureIsEmptyMessage;

    private static final String CONFIG_FILE_PATH = "./config.properties";
    private static final String MAIL_PASSWORD = "mail.password";
    private static final String MAIL_HOST = "mail.host";
    private static final String MAIL_NAME = "mail.name";
    private static final String MAIL_USERNAME = "mail.username";
    private static final String MAIL_PORT = "mail.port";
    private static final String MAIL_SMTP_AUTH = "mail.smtp.auth";
    private static final String MAIL_SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";
    private static final String SESSION_INTERVAL = "sessionManager.sessionTime";
    private static final String OBSOLETE_PDB_IDS = "obsoletePdbIds";
    private static final String CURRENT_PDB_IDS = "currentPdbIds";
    private static final String SESSION_MAP_TIME_DELAY = "timerTask.sessionMap.timeDelay";
    private static final String SESSION_MAP_TIME_INTERVAL = "timerTask.sessionMap.timeInterval";
    private static final String PDB_IDS_SET_TIME_DELAY = "timerTask.currentPdbIdList.timeDelay";
    private static final String PDB_IDS_SET_TIME_INTERVAL = "timerTask.currentPdbIdList.timeInterval";
    private static final String ERROR_MESSAGE = "error.message";
    private static final String EXCEPTION_NOT_FOUND_MESSAGE = "error.structureNotFound.message";
    private static final String EXCEPTION_INVALID_EMAIL_ADDRESS = "error.invalidMail";
    private static final String EXCEPTION_STRUCTURE_IS_EMPTY = "error.structureIsEmpty";


    /**
     * it loads properties, from config.properties file, to properties variable.
     *
     * @throws IOException when config.properties file does not exist or it is not readable.
     */
    public ConfigService() throws IOException {
        this.properties = new Properties();
        this.properties.load(this.getClass().getClassLoader().getResourceAsStream(CONFIG_FILE_PATH));

        this.emailPassword = this.properties.getProperty(MAIL_PASSWORD);
        this.emailUsername = this.properties.getProperty(MAIL_USERNAME);
        this.emailHost = this.properties.getProperty(MAIL_HOST);
        this.emailName = this.properties.getProperty(MAIL_NAME);
        this.emailPort = this.properties.getProperty(MAIL_PORT);
        this.smtpStarttls = this.properties.getProperty(MAIL_SMTP_STARTTLS_ENABLE);
        this.smtpAuth = this.properties.getProperty(MAIL_SMTP_AUTH);

        this.sessionInterval = this.properties.getProperty(SESSION_INTERVAL);

        this.obsoletePdbIdsSetUrl = this.properties.getProperty(OBSOLETE_PDB_IDS);
        this.currentPdbIdsSetUrl = this.properties.getProperty(CURRENT_PDB_IDS);

        this.sessionMapDelay = this.properties.getProperty(SESSION_MAP_TIME_DELAY);
        this.sessionMapInterval = this.properties.getProperty(SESSION_MAP_TIME_INTERVAL);
        this.pdbIdsSetDelay = this.properties.getProperty(PDB_IDS_SET_TIME_DELAY);
        this.pdbIdsSetInterval = this.properties.getProperty(PDB_IDS_SET_TIME_INTERVAL);

        this.errorMessage = this.properties.getProperty(ERROR_MESSAGE);
        this.exceptionNotFoundMessage = this.properties.getProperty(EXCEPTION_NOT_FOUND_MESSAGE);
        this.exceptionInvalidEmailAddressMessage = this.properties.getProperty(EXCEPTION_INVALID_EMAIL_ADDRESS);
        this.exceptionStructureIsEmptyMessage = this.properties.getProperty(EXCEPTION_STRUCTURE_IS_EMPTY);
    }
}


