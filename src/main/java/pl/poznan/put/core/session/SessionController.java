package pl.poznan.put.core.session;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.poznan.put.core.session.pdbid.PdbIdValidator;
import pl.poznan.put.exceptions.InvalidArgumentException;

import java.util.UUID;
import javax.inject.Inject;

@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class SessionController {

    private final PdbIdValidator pdbIdValidator;
    private final SessionManager sessionManager;

    @RequestMapping("/pdbId/{id}")
    public final HttpEntity<UUID> sessionId(@PathVariable("id") final String id) throws InvalidArgumentException {
        pdbIdValidator.validate(id);
        try {
            return new ResponseEntity<>(sessionManager.getSession(id), HttpStatus.OK);
        } catch (Exception ex) {
            log.error("Unable to parse session request. {}", ex);
            throw new InvalidArgumentException(ex);
        }
    }
}
