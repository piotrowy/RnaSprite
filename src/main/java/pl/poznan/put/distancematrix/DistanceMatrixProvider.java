package pl.poznan.put.distancematrix;

import pl.poznan.put.exceptions.StructureIsEmptyException;
import pl.poznan.put.rnamatrix.Matrix;
import pl.poznan.put.rnamatrix.MatrixProvider;
import pl.poznan.put.structure.PdbStructure;

import java.util.List;
import java.util.stream.Collectors;

public class DistanceMatrixProvider extends MatrixProvider<String, String, String, String> {

    public DistanceMatrixProvider(final DistanceMatrixCalculation distanceMatrixCalculation) {
        super.setCalculationMethod(distanceMatrixCalculation);
    }

    @Override
    public List<Matrix<String, String, String>> create(PdbStructure structure, String args) {
        if (!structure.getModels().isEmpty()) {
            return structure.getModels().stream().map(model -> super.getCalculationMethod().calculateMatrix(model, args)).collect(Collectors.toList());
        }
        throw new StructureIsEmptyException(structure.toString());
    }
}

