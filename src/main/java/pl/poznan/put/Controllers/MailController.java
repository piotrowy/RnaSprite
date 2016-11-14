package pl.poznan.put.Controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import pl.poznan.put.Error.Exceptions.InvalidMailAddress;
import pl.poznan.put.Mail.MailValidator;

import javax.inject.Inject;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class MailController {

    private final MailValidator mailValidator;

    private void validateMail(final String address) throws InvalidMailAddress {
        if (!this.mailValidator.validate(address)) {
            throw new InvalidMailAddress();
        }
    }
}
