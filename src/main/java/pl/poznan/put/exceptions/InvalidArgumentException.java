package pl.poznan.put.exceptions;

public class InvalidArgumentException extends CustomException {
    public InvalidArgumentException(final String message) {
        super(message);
    }

    public InvalidArgumentException(final Exception ex) {
        super(ex);
    }

}

