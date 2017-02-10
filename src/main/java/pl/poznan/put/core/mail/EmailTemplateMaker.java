package pl.poznan.put.core.mail;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.core.env.Environment;

import java.io.StringWriter;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Named;

import static pl.poznan.put.core.mail.SendMailManager.FROM;

@Data
@Builder
@Named
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class EmailTemplateMaker {

    private static final String RECEIVER = "receiver";
    private static final String SENDER = "sender";

    private final VelocityEngine velocityEngine;
    private final Environment env;

    public String mergeTemplateIntoEmailText(EmailTemplate emailTemplate, String receiver, Map<String, Object> data) {
        velocityEngine.init();
        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put(RECEIVER, receiver);
        velocityContext.put(SENDER, env.getProperty(FROM));
        data.keySet().forEach(key -> velocityContext.put(key, data.get(key)));
        StringWriter writer = new StringWriter();
        Template mailTemplate = velocityEngine.getTemplate(emailTemplate.toString());
        mailTemplate.merge(velocityContext, writer);
        return writer.toString();
    }
}
