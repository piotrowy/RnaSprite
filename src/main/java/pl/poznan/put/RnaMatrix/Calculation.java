package pl.poznan.put.rnamatrix;

import pl.poznan.put.pdb.analysis.PdbChain;
import pl.poznan.put.pdb.analysis.PdbCompactFragment;
import pl.poznan.put.pdb.analysis.PdbModel;

import java.text.DecimalFormat;
import java.util.Optional;

public interface Calculation<T, U, V, X> {

    Matrix<T, U, V> calculateMatrix(final PdbModel model, Optional<X> arg);

    DecimalFormat DECIMAL_FORMAT_2 = new DecimalFormat("#,###,###,##0.00");

    static PdbCompactFragment pdbChainToCompactFragment(PdbChain chain) {
        return new PdbCompactFragment(String.valueOf(chain.getIdentifier()), chain.getResidues());
    }
}
