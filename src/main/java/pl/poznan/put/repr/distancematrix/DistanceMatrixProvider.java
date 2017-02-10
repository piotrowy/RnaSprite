package pl.poznan.put.repr.distancematrix;

import pl.poznan.put.core.rnamatrix.MatrixProvider;
import pl.poznan.put.core.rnamatrix.calculation.StructureCalculation;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class DistanceMatrixProvider extends MatrixProvider<DistanceMatrix, ResiduesAtomsSpecification> {

    @Inject
    public DistanceMatrixProvider(final StructureCalculation<DistanceMatrix, ResiduesAtomsSpecification> distanceMatrixCalculation) {
        super(distanceMatrixCalculation);
    }
}

