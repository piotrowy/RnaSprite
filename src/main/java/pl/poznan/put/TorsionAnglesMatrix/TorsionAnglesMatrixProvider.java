package pl.poznan.put.torsionanglesmatrix;

import pl.poznan.put.exceptions.StructureIsEmptyException;
import pl.poznan.put.rna.torsion.RNATorsionAngleType;
import pl.poznan.put.rnamatrix.Matrix;
import pl.poznan.put.rnamatrix.MatrixProvider;
import pl.poznan.put.structure.PdbStructure;

import javax.inject.Named;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Named
public class TorsionAnglesMatrixProvider extends MatrixProvider<ResidueInfo, String, AngleData, Set<RNATorsionAngleType>> {

    public TorsionAnglesMatrixProvider(final TorsionAnglesMatrixCalculation torsionAnglesMatrixCalculation) {
        super.setCalculationMethod(torsionAnglesMatrixCalculation);
    }

    @Override
    public final List<Matrix<ResidueInfo, String, AngleData>> get(final PdbStructure structure, Optional<Set<RNATorsionAngleType>> angles) {
        if (!structure.getModels().isEmpty()) {
            return structure.getModels().stream()
                    .map(model -> super.getCalculationMethod().calculateMatrix(model, angles))
                    .collect(Collectors.toList());
        }
        throw new StructureIsEmptyException(structure.toString());
    }
}
