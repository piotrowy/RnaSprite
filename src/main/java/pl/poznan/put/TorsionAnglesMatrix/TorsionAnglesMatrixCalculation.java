package pl.poznan.put.TorsionAnglesMatrix;

import pl.poznan.put.RnaMatrix.MatrixCalculation;
import pl.poznan.put.RnaMatrix.RnaMatrix;
import pl.poznan.put.Structure.PdbStructure;

public class TorsionAnglesMatrixCalculation implements MatrixCalculation {

    @Override
    public final RnaMatrix calculateMatrix(final PdbStructure structure) {
        if (!structure.getModels().isEmpty()) {
            return null;
        }
        return new RnaMatrix();
    }
}
