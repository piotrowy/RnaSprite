package pl.poznan.put.core.mail;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.poznan.put.core.csv.CsvMatrix;
import pl.poznan.put.core.session.SessionValidator;
import pl.poznan.put.core.session.caches.EmptyCacheException;
import pl.poznan.put.core.session.caches.GenericMatrixCacheManager;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;
import javax.inject.Inject;

@Slf4j
@RestController
@RequestMapping("/email")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class EmailController {

    private final EmailValidator emailValidator;
    private final SessionValidator sessionValidator;
    private final Environment env;
    private final SendEmailManager sendEmailManager;
    private final Set<GenericMatrixCacheManager> matrixCacheManagers;

    @RequestMapping("send/{sessionId}/{address:.+}")
    public final void sendMail(@PathVariable("sessionId") final String sessionId, @PathVariable("address") final String address)
            throws InvalidMailAddressException, EmptyCacheException, IOException {
        emailValidator.validate(address);
        sessionValidator.validate(sessionId);
        for (GenericMatrixCacheManager manager : matrixCacheManagers) {
            if (manager.exists(UUID.fromString(sessionId))) {
                CsvMatrix.builder()
                        .sessionId(UUID.fromString(sessionId))
                        .mtxStructure(manager.getMatrixCache(UUID.fromString(sessionId)).getMatrix())
                        .build()
                        .sendInMail(sendEmailManager, address, env.getProperty("email.subject"));
            }
        }
    }
}
