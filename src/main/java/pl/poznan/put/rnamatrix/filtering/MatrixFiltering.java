package pl.poznan.put.rnamatrix.filtering;

import pl.poznan.put.rnamatrix.model.Matrix;

@FunctionalInterface
public interface MatrixFiltering<T extends Matrix, U> {

    T filterMatrix(U specification);
}
