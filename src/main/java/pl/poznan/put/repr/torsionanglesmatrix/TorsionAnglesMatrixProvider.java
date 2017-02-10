package pl.poznan.put.repr.torsionanglesmatrix;

import pl.poznan.put.core.rnamatrix.FilteringProvider;
import pl.poznan.put.core.rnamatrix.MatrixProvider;
import pl.poznan.put.core.rnamatrix.calculation.StructureCalculation;

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
