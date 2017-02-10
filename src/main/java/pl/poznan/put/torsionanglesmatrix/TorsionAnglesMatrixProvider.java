package pl.poznan.put.torsionanglesmatrix;

import pl.poznan.put.rnamatrix.FilteringProvider;
import pl.poznan.put.rnamatrix.MatrixProvider;
import pl.poznan.put.rnamatrix.calculation.StructureCalculation;

import java.util.Set;
import javax.inject.Inject;
import javax.inject.Named;

@Named
public class TorsionAnglesMatrixProvider extends MatrixProvider<TorsionAnglesMatrix, Set<String>>
        implements FilteringProvider<TorsionAnglesMatrix, Set<String>> {

    @Inject
    public TorsionAnglesMatrixProvider(final StructureCalculation<TorsionAnglesMatrix, Set<String>> structureCalculation) {
        super(structureCalculation);
    }
}
