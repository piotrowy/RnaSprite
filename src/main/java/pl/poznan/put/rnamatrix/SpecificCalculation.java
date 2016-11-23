package pl.poznan.put.rnamatrix;

import pl.poznan.put.pdb.analysis.PdbModel;

public interface SpecificCalculation<T, U, V, X> extends Calculation<T, U, V> {

    Matrix<T, U, V> calculateSpecificMatrix(final PdbModel model, X... args);
}
