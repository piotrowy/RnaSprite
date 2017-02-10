package pl.poznan.put.util;

import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class OptionalConsumer<T> implements Consumer<Optional<T>> {

    private final Consumer<T> c;
    private final Runnable r;

    public static <T> OptionalConsumer<T> of(Consumer<T> c, Runnable r) {
        return new OptionalConsumer<>(c, r);
    }

    @Override
    public void accept(Optional<T> t) {
        if (t.isPresent()) {
            c.accept(t.get());
        } else {
            r.run();
        }
    }
}
