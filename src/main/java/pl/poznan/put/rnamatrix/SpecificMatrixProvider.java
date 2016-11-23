package pl.poznan.put.rnamatrix;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import pl.poznan.put.structure.PdbStructure;

import java.util.List;

@RequiredArgsConstructor
public abstract class SpecificMatrixProvider<T, U, V, X> implements SpecificMatrixFactory<List<Matrix<T, U, V>>, PdbStructure, X> {

    /**
     * It is a variable which stores reference to object which
     * implements Calculation interface. It has got
     * calculate method to obtain proper Matrix.
     */
    @Getter
    @Setter
    private SpecificCalculation<T, U, V, X> calculationMethod;
}
