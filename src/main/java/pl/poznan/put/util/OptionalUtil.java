package pl.poznan.put.util;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class OptionalUtil<T> {

    private final Optional<T> optional;

    public static <T> OptionalUtil<T> of(Optional<T> optional) {
        return new OptionalUtil<>(optional);
    }

    public void ifNotPresent(Runnable r) {
        if (!optional.isPresent())
            r.run();
    }

}
