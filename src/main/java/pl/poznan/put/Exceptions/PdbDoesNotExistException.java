package pl.poznan.put.Exceptions;

public class PdbDoesNotExistException extends RuntimeException {
    public PdbDoesNotExistException(String message) {
        super(message);
    }
}
