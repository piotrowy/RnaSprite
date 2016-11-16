package pl.poznan.put.DistanceMatrix;

import pl.poznan.put.RnaMatrix.RnaMatrixProvider;

import javax.inject.Inject;

public class DistanceMatrixProvider extends RnaMatrixProvider {

    @Inject
    public DistanceMatrixProvider(DistancesCalculation torsionAnglesMatrixCalculation) {
        super.calculationMethod = torsionAnglesMatrixCalculation;
    }
}
