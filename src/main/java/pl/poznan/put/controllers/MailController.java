package pl.poznan.put.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.poznan.put.exceptions.InvalidMailAddressException;
import pl.poznan.put.mail.MailValidator;

import javax.inject.Inject;

@Slf4j
@RestController
@RequestMapping("/mail")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class MailController {

    private final MailValidator mailValidator;

    @RequestMapping("distanceMatrix/{sessionId}/{address}")
    public final boolean distanceMatrix(@PathVariable("sessionId") final String sessionId, @PathVariable("address") final String address) {
        this.validateMail(address);
        return true;
    }

    @RequestMapping("torsionAnglesMatrix/{sessionId}/{address}")
    public final boolean torsionAnglesMatrix(@PathVariable("sessionId") final String sessionId, @PathVariable("address") final String address) {
        this.validateMail(address);
        return true;
    }

    @RequestMapping("all/{sessionId}/{address}")
    public final boolean all(@PathVariable("sessionId") final String sessionId, @PathVariable("address") final String address) {
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
