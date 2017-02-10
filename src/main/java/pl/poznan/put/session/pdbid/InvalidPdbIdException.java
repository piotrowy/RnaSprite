package pl.poznan.put.session.pdbid;

import pl.poznan.put.util.exceptions.InvalidArgumentException;

public class InvalidPdbIdException extends InvalidArgumentException {
    public InvalidPdbIdException(final String message) {
        super(message);
    }
}
