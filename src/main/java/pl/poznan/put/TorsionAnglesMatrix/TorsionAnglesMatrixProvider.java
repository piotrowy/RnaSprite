package pl.poznan.put.TorsionAnglesMatrix;

import pl.poznan.put.Exceptions.StructureIsEmptyException;
import pl.poznan.put.RnaMatrix.RnaMatrix;
import pl.poznan.put.RnaMatrix.RnaMatrixProvider;
import pl.poznan.put.Structure.PdbStructure;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

public class TorsionAnglesMatrixProvider extends RnaMatrixProvider {

    @Inject
    public TorsionAnglesMatrixProvider(TorsionAnglesCalculation torsionAnglesMatrixCalculation) {
        super.calculationMethod = torsionAnglesMatrixCalculation;
    }

    @Override
    public List<RnaMatrix> get(PdbStructure structure) {
        if (!structure.getModels().isEmpty()) {
            return structure.getModels().stream().map(model -> calculationMethod.calculateMatrix(model)).collect(Collectors.toList());
        }
        throw new StructureIsEmptyException(structure.toString());
    }
}
