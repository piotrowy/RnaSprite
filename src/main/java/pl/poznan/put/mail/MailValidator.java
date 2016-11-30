package pl.poznan.put.mail;

import org.springframework.context.annotation.Configuration;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Configuration
public class MailValidator {

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private Pattern pattern;
    private Matcher matcher;

    public MailValidator() {
        this.pattern = pattern.compile(EMAIL_PATTERN);
    }

    public final boolean validate(final String address) {
        this.matcher = pattern.matcher(address);
        return matcher.matches();
    }
}
