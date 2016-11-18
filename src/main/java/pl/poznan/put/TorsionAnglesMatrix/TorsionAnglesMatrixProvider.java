package pl.poznan.put.TorsionAnglesMatrix;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import pl.poznan.put.Exceptions.StructureIsEmptyException;
import pl.poznan.put.RnaMatrix.RnaMatrix;
import pl.poznan.put.RnaMatrix.RnaMatrixProvider;
import pl.poznan.put.Structure.PdbStructure;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TorsionAnglesMatrixProvider extends RnaMatrixProvider<ResidueInfo, String, AngleData> {

    public TorsionAnglesMatrixProvider(TorsionAnglesCalculation torsionAnglesMatrixCalculation) {
        super.calculationMethod = torsionAnglesMatrixCalculation;
    }

    @Override
    public List<RnaMatrix<ResidueInfo, String, AngleData>> get(PdbStructure structure) {
        if (!structure.getModels().isEmpty()) {
            return structure.getModels().stream().map(model -> calculationMethod.calculateMatrix(model)).collect(Collectors.toList());
        }
        throw new StructureIsEmptyException(structure.toString());
    }
}
