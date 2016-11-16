package pl.poznan.put.Util;

import pl.poznan.put.RnaMatrix.RnaMatrix;
import pl.poznan.put.pdb.analysis.PdbModel;

public interface Calculable {

    /**
     * @return some derivative matrix.
     */
    RnaMatrix calculateMatrix(PdbModel model);
}
