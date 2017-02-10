package pl.poznan.put.core.mail;

import pl.poznan.put.util.Validator;

import java.util.regex.Pattern;
import javax.inject.Named;

@Named
public class MailValidator implements Validator<String, InvalidMailAddressException> {

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    @Override
    public boolean validate(String mail) throws InvalidMailAddressException {
        if (Pattern.compile(EMAIL_PATTERN)
                .matcher(mail)
                .matches()) {
            return true;
        }
        throw new InvalidMailAddressException(mail);
    }
}
