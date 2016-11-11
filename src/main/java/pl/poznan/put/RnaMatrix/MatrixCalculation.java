package pl.poznan.put.RnaMatrix;

import pl.poznan.put.Structure.PdbStructure;

public interface MatrixCalculation {

    /**
     * @return some derivative matrix.
     */
    RnaMatrix calculateMatrix(PdbStructure structure);
}
