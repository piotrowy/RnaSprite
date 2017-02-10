package pl.poznan.put.core.mail;

import pl.poznan.put.exceptions.InvalidArgumentException;

public class InvalidMailAddressException extends InvalidArgumentException {
    InvalidMailAddressException(final String message) {
        super(message);
    }
}
