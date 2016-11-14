package pl.poznan.put.Controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.poznan.put.Exceptions.InvalidMailAddressException;
import pl.poznan.put.Mail.MailValidator;

import javax.inject.Inject;

@Slf4j
@RestController
@RequestMapping("/mail")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class MailController {

    private final MailValidator mailValidator;

    @RequestMapping("distanceMatrix/{sessionId}/{address}")
    public boolean distanceMatrix(@PathVariable("sessionId") final String sessionId, @PathVariable("address") final String address) {
        this.validateMail(address);
        return true;
    }

    @RequestMapping("torsionAnglesMatrix/{sessionId}/{address}")
    public boolean torsionAnglesMatrix(@PathVariable("sessionId") final String sessionId, @PathVariable("address") final String address) {
        this.validateMail(address);
        return true;
    }

    @RequestMapping("all/{sessionId}/{address}")
    public boolean all(@PathVariable("sessionId") final String sessionId, @PathVariable("address") final String address) {
        this.validateMail(address);
        return true;
    }

    private void validateMail(final String address) throws InvalidMailAddressException {
        if (!this.mailValidator.validate(address)) {
            log.error("Wrong email address: {}", address);
            throw new InvalidMailAddressException(address);
        }
    }
}
