package pl.poznan.put.RnaMatrix;

public interface Downloadable<T, U> {

    T get(U obj);
}
