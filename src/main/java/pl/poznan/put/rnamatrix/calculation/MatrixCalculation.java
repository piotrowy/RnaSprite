package pl.poznan.put.rnamatrix.calculation;

import pl.poznan.put.pdb.analysis.PdbChain;
import pl.poznan.put.pdb.analysis.PdbCompactFragment;
import pl.poznan.put.rnamatrix.model.Matrix;

import java.text.DecimalFormat;
import java.util.List;

@FunctionalInterface
public interface MatrixCalculation<T extends Matrix, U> {

    DecimalFormat DECIMAL_FORMAT_2 = new DecimalFormat("#,###,###,##0.00");

    static PdbCompactFragment pdbChainToCompactFragment(PdbChain chain) {
        return new PdbCompactFragment(String.valueOf(chain.getIdentifier()), chain.getResidues());
    }

    List<T> calculate(final PdbChain chain, U specification);
}
