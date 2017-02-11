package pl.poznan.put.core.mail;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

import java.io.File;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import javax.mail.MessagingException;

@Data
@Slf4j
@Named
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class SendEmailManager {

    public static final String FROM = "mail.from";

    private final JavaMailSender mailSender;
    private final Environment env;

    public final void sendMail(String receiver, String subject, String text, File attachment) {
        sendMail(receiver, subject, text, Collections.singletonList(attachment));
    }

    public final void sendMail(String receiver, String subject, String text, List<File> attachments) {
        MimeMessagePreparator preparator = mimeMessage -> {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);
            message.setFrom(env.getProperty(FROM));
            message.setTo(receiver);
            message.setSubject(subject);

            for (File attachment : attachments) {
                try {
                    message.addAttachment(attachment.getName(), attachment);
                } catch (MessagingException ex) {
                    log.error("Error during adding attachment to file mail: {}", ex);
                    break;
                }
            }

            message.setText(text, true);
        };
        log.info("Sending email to: {}, subject: {}, text: {}", receiver, subject, text);
        mailSender.send(preparator);
    }
}

