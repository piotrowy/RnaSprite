package pl.poznan.put.DistanceMatrix;

import pl.poznan.put.RnaMatrix.RnaMatrix;
import pl.poznan.put.RnaMatrix.RnaMatrixProvider;
import pl.poznan.put.Structure.PdbStructure;

import javax.inject.Inject;
import java.util.List;

public class DistanceMatrixProvider extends RnaMatrixProvider {

    @Inject
    public DistanceMatrixProvider(DistancesCalculation torsionAnglesMatrixCalculation) {
        super.calculationMethod = torsionAnglesMatrixCalculation;
    }

    @Override
    public List<RnaMatrix> get(PdbStructure obj) {
        return null;
    }
}
