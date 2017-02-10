package pl.poznan.put.core.mail;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfiguration {

    private static final String PORT = "mail.port";
    private static final String HOST = "mail.host";
    private static final String USERNAME = "mail.username";
    private static final String PASSWORD = "mail.password";
    private static final String MAIL_SMTP_AUTH = "mail.smtp.auth";
    private static final String MAIL_SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";
    private static final String SOCKET_FACTORY_PORT = "mail.smtp.socketFactory.port";
    private static final String SOCKET_FACTORY_CLASS = "mail.smtp.socketFactory.class";

    @Bean
    public JavaMailSender javaMailSender(Environment env) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        Properties mailProperties = mailSender.getJavaMailProperties();
        mailProperties.setProperty(MAIL_SMTP_AUTH, env.getProperty(MAIL_SMTP_AUTH));
        mailProperties.setProperty(MAIL_SMTP_STARTTLS_ENABLE, env.getProperty(MAIL_SMTP_STARTTLS_ENABLE));
        mailProperties.setProperty(SOCKET_FACTORY_PORT, env.getProperty(PORT));
        mailProperties.setProperty(SOCKET_FACTORY_CLASS, env.getProperty(SOCKET_FACTORY_CLASS));
        mailSender.setHost(env.getProperty(HOST));
        mailSender.setPort(env.getProperty(PORT, Integer.class));
        mailSender.setUsername(env.getProperty(USERNAME));
        mailSender.setPassword(env.getProperty(PASSWORD));
        return mailSender;
    }
}
