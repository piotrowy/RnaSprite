package pl.poznan.put.rnamatrix;

import pl.poznan.put.pdb.analysis.PdbChain;
import pl.poznan.put.pdb.analysis.PdbCompactFragment;
import pl.poznan.put.pdb.analysis.PdbModel;

public interface Calculation<T, U, V> {

    /**
     * @return some derivative matrix.
     */
    Matrix<T, U, V> calculateMatrix(final PdbModel model);

    static PdbCompactFragment pdbChainToCompactFragment(PdbChain chain) {
        return new PdbCompactFragment(String.valueOf(chain.getIdentifier()), chain.getResidues());
    }
}
