package pl.poznan.put.core.rnamatrix.filtering;

import pl.poznan.put.core.rnamatrix.model.Matrix;

@FunctionalInterface
public interface MatrixFiltering<T extends Matrix, U> {

    T filterMatrix(U specification);
}
