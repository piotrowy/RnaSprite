package pl.poznan.put.mail;

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

import static pl.poznan.put.mail.EmailTemplate.ERROR_TEMPLATE;

@Data
@Slf4j
@Named
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class SendMailManager {

    public static final String FROM = "mail.from";

    private final JavaMailSender mailSender;
    private final EmailTemplateMaker emailTemplateMaker;
    private final Environment env;

    public final void sendMail(String receiver, String subject, EmailTemplate emailTemplate, File attachment) {
        sendMail(receiver, subject, emailTemplate, Collections.singletonList(attachment));
    }

    public final void sendMail(String receiver, String subject, EmailTemplate emailTemplate, List<File> attachments) {
        MimeMessagePreparator preparator = mimeMessage -> {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
            message.setFrom(env.getProperty(FROM));
            message.setTo(receiver);
            message.setSubject(subject);

            String text = emailTemplateMaker.mergeTemplateIntoEmailText(emailTemplate, receiver, Collections.emptyMap());

            for (File attachment : attachments) {
                try {
                    message.addAttachment(attachment.getName(), attachment);
                } catch (MessagingException ex) {
                    log.error("Error during adding attachment to file mail: {}", ex);
                    text += emailTemplateMaker.mergeTemplateIntoEmailText(ERROR_TEMPLATE, receiver, Collections.emptyMap());
                    break;
                }
            }

            message.setText(text, true);
        };
        log.info("Sending email to: {}, subject: {}, text: {}", receiver, subject, emailTemplate);
        mailSender.send(preparator);
    }
}

