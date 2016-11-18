package pl.poznan.put.RnaMatrix;

import pl.poznan.put.pdb.analysis.PdbModel;

public interface Calculable<T, U, V> {

    /**
     * @return some derivative matrix.
     */
    RnaMatrix<T, U, V> calculateMatrix(final PdbModel model);
}
