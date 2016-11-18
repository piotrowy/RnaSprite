package pl.poznan.put.RnaMatrix;

import pl.poznan.put.pdb.analysis.PdbChain;
import pl.poznan.put.pdb.analysis.PdbCompactFragment;

public interface MatrixCalculable<T, U, V> extends Calculable<T, U, V> {

    static PdbCompactFragment pdbChainToCompactFragment(PdbChain chain) {
        return new PdbCompactFragment(String.valueOf(chain.getIdentifier()), chain.getResidues());
    }
}
