package pl.poznan.put.torsionanglesmatrix;

import org.springframework.stereotype.Component;
import pl.poznan.put.exceptions.StructureIsEmptyException;
import pl.poznan.put.rna.torsion.RNATorsionAngleType;
import pl.poznan.put.rnamatrix.Matrix;
import pl.poznan.put.rnamatrix.SpecificMatrixProvider;
import pl.poznan.put.structure.PdbStructure;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TorsionAnglesMatrixProvider extends SpecificMatrixProvider<ResidueInfo, String, AngleData, RNATorsionAngleType> {

    public TorsionAnglesMatrixProvider(final TorsionAnglesMatrixCalculation torsionAnglesMatrixCalculation) {
        super.setCalculationMethod(torsionAnglesMatrixCalculation);
    }

    @Override
    public final List<Matrix<ResidueInfo, String, AngleData>> get(final PdbStructure structure) {
        if (!structure.getModels().isEmpty()) {
            return structure.getModels().stream().map(model -> super.getCalculationMethod().calculateMatrix(model)).collect(Collectors.toList());
        }
        throw new StructureIsEmptyException(structure.toString());
    }

    @Override
    public final List<Matrix<ResidueInfo, String, AngleData>> getSpecific(final PdbStructure structure, final RNATorsionAngleType... args) {
        if (!structure.getModels().isEmpty()) {
            return structure.getModels().stream().map(model -> super.getCalculationMethod().calculateSpecificMatrix(model, args))
                    .collect(Collectors.toList());
        }
        throw new StructureIsEmptyException(structure.toString());
    }
}
