package pl.poznan.put.TorsionAnglesMatrix;

import pl.poznan.put.RnaMatrix.MatrixCalculation;
import pl.poznan.put.RnaMatrix.RnaMatrix;
import pl.poznan.put.StructureContainer;

/**
 * Created by piotrowy on 19.10.2016.
 */
public class TorsionAnglesMatrixCalculation implements MatrixCalculation {

    @Override
    public RnaMatrix calculateMatrix(StructureContainer structure) {
        if (!structure.getStructureList().isEmpty()) {
            return null;
        }
        return new RnaMatrix();
    }
}
