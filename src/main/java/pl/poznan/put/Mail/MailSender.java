package pl.poznan.put.Mail;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import pl.poznan.put.Util.ConfigService;

import javax.inject.Inject;
import java.util.Properties;

@Data
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class MailSender {

    private final ConfigService configService;

    private static final String MAIL_SMTP_AUTH = "mail.smtp.auth";
    private static final String MAIL_SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";

    @Bean
    public final JavaMailSender getMailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

        javaMailSender.setUsername(this.configService.getEmailUsername());
        javaMailSender.setPassword(this.configService.getEmailPassword());
        javaMailSender.setHost(this.configService.getEmailHost());
        javaMailSender.setPort(Integer.parseInt(this.configService.getEmailPort()));
        this.setMailProperties(javaMailSender.getJavaMailProperties());

        return javaMailSender;
    }

    private void setMailProperties(final Properties properties) {
        properties.setProperty(MAIL_SMTP_AUTH, this.configService.getSmtpAuth());
        properties.setProperty(MAIL_SMTP_STARTTLS_ENABLE, this.configService.getSmtpStarttls());
    }
}
