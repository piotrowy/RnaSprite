package pl.poznan.put.Exceptions;

public class InvalidMailAddressException extends CustomException {
    public InvalidMailAddressException(String message) {
        super(message);
    }
}
