package pl.poznan.put.structure;

import pl.poznan.put.util.exceptions.CustomException;

public class EmptyStructureException extends CustomException {
    public EmptyStructureException(final String message) {
        super(message);
    }

}

