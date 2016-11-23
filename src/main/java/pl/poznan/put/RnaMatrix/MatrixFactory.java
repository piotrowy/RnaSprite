package pl.poznan.put.rnamatrix;

public interface MatrixFactory<T, U> {

    T get(U obj);
}
