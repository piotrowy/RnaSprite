package pl.poznan.put.RnaMatrix;

import lombok.RequiredArgsConstructor;
import pl.poznan.put.Structure.PdbStructure;
import pl.poznan.put.Util.Calculable;

import java.util.List;

@RequiredArgsConstructor
public abstract class RnaMatrixProvider implements Downloadable<List<RnaMatrix>, PdbStructure> {

    /**
     * It is a variable which stores reference to object which
     * implements Calculable interface. It has got
     * calculate method to obtain proper RnaMatrix.
     */
    protected Calculable calculationMethod;
}
