package pl.poznan.put.distancematrix;

import pl.poznan.put.exceptions.StructureIsEmptyException;
import pl.poznan.put.rnamatrix.Matrix;
import pl.poznan.put.rnamatrix.MatrixProvider;
import pl.poznan.put.structure.PdbStructure;
import pl.poznan.put.torsionanglesmatrix.AngleData;
import pl.poznan.put.torsionanglesmatrix.ResidueInfo;

import java.util.List;
import java.util.stream.Collectors;

public class DistanceMatrixProvider extends MatrixProvider<ResidueInfo, String, AngleData> {

    public DistanceMatrixProvider(final DistanceMatrixCalculation distanceMatrixCalculation) {
        super.setCalculationMethod(distanceMatrixCalculation);
    }

    @Override
    public List<Matrix<ResidueInfo, String, AngleData>> get(PdbStructure structure) {
        if (!structure.getModels().isEmpty()) {
            return structure.getModels().stream().map(model -> super.getCalculationMethod().calculateMatrix(model)).collect(Collectors.toList());
        }
        throw new StructureIsEmptyException(structure.toString());
    }
}
