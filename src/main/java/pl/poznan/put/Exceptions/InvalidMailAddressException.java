package pl.poznan.put.exceptions;

public class InvalidMailAddressException extends CustomException {
    public InvalidMailAddressException(final String message) {
        super(message);
    }
}
