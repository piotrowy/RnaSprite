package pl.poznan.put.Mail;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailParseException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import pl.poznan.put.Util.ConfigService;

import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Data
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class MailManager {

    private final JavaMailSender mailSender;
    private final ConfigService configService;

    public final void sendMail(final MailContent content) {
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(this.configService.getEmailUsername());
            helper.setTo(content.getTo());
            helper.setSubject(content.getSubject());
            helper.setText(content.getText());

            this.addAttachments(helper, content);
        } catch (MessagingException ex) {
            throw new MailParseException(ex);
        }
        mailSender.send(message);
    }

    private void addAttachments(final MimeMessageHelper helper, final MailContent content) throws MessagingException {
        if (content.getAttachments() != null) {
            for (File file : content.getAttachments()) {
                helper.addAttachment(file.getName(), file);
            }
        }
    }
}

