package pl.poznan.put.exceptions;

public class CustomException extends RuntimeException {
    public CustomException(final String message) {
        super(message);
    }
}
