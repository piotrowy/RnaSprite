package pl.poznan.put.RnaMatrix;

import lombok.RequiredArgsConstructor;
import pl.poznan.put.Structure.PdbStructure;

import java.util.List;

@RequiredArgsConstructor
public abstract class RnaMatrixProvider<T, U, V> implements Downloadable<List<RnaMatrix<T, U, V>>, PdbStructure> {

    /**
     * It is a variable which stores reference to object which
     * implements Calculable interface. It has got
     * calculate method to obtain proper RnaMatrix.
     */
    protected Calculable<T, U, V> calculationMethod;
}
