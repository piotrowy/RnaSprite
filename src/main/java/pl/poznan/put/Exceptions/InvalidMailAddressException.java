package pl.poznan.put.Exceptions;

public class InvalidMailAddressException extends RuntimeException {
    public InvalidMailAddressException(String message) {
        super(message);
    }
}
