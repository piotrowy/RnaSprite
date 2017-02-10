package pl.poznan.put.util;

import pl.poznan.put.util.exceptions.InvalidArgumentException;

@FunctionalInterface
public interface Validator<T, U extends InvalidArgumentException> {

    default boolean isValid(T param) {
        try {
            validate(param);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    boolean validate(T param) throws U;
}
