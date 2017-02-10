package pl.poznan.put.session;

import lombok.RequiredArgsConstructor;
import pl.poznan.put.session.caches.EmptyCacheException;
import pl.poznan.put.util.Validator;

import java.util.UUID;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class SessionValidator implements Validator<String, EmptyCacheException> {

    private final SessionManager sessionManager;

    @Override
    public boolean validate(String param) throws EmptyCacheException {
        if (sessionManager.sessionExists(UUID.fromString(param))) {
            return true;
        }
        throw new EmptyCacheException(param);
    }
}
