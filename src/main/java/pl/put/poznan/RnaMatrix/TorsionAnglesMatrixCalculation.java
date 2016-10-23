package pl.put.poznan.RnaMatrix;

import pl.put.poznan.StructureContainer;

import java.text.DecimalFormat;

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
