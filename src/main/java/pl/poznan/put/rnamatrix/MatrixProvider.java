package pl.poznan.put.rnamatrix;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
public abstract class MatrixProvider<T, U, V, X> implements MatrixFactory<T, U, V, X> {

    /**
     * It is a variable which stores reference to object which
     * implements Calculation interface. It has got
     * calculate method to obtain proper Matrix.
     */
    @Getter
    @Setter
    private Calculation<T, U, V, X> calculationMethod;
}
