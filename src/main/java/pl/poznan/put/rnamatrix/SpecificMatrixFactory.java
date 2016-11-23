package pl.poznan.put.rnamatrix;

public interface SpecificMatrixFactory<T, U, V> extends  MatrixFactory<T, U> {

    T getSpecific(U obj, V... args);
}
