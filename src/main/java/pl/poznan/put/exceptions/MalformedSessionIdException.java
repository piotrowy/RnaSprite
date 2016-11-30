package pl.poznan.put.exceptions;

public class MalformedSessionIdException extends CustomException {
    public MalformedSessionIdException(final String message) {
        super(message);
    }
}
