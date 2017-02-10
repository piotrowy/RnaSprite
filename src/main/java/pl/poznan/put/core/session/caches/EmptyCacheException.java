package pl.poznan.put.core.session.caches;

import pl.poznan.put.util.exceptions.InvalidArgumentException;

public class EmptyCacheException extends InvalidArgumentException {
    public EmptyCacheException(final String message) {
        super(message);
    }

}
