package pl.poznan.put.exceptions;

import lombok.Builder;

@Builder
public class CustomException extends Exception {

    private String message;

    public CustomException(final String message) {
        super(message);
    }

    public CustomException(final Exception ex) {
        super(ex);
    }
}
