package pl.poznan.put.distancematrix;

import pl.poznan.put.rnamatrix.MatrixProvider;
import pl.poznan.put.rnamatrix.calculation.StructureCalculation;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class DistanceMatrixProvider extends MatrixProvider<DistanceMatrix, ResiduesAtomsSpecification> {

    @Inject
    public DistanceMatrixProvider(final StructureCalculation<DistanceMatrix, ResiduesAtomsSpecification> distanceMatrixCalculation) {
        super(distanceMatrixCalculation);
    }
}

