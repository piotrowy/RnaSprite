package pl.poznan.put.exceptions;

public class PdbDoesNotExistException extends CustomException {
    public PdbDoesNotExistException(final String message) {
        super(message);
    }
}
