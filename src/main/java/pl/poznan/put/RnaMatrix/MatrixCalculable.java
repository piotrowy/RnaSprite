package pl.poznan.put.RnaMatrix;

import pl.poznan.put.Util.Calculable;
import pl.poznan.put.pdb.analysis.PdbChain;
import pl.poznan.put.pdb.analysis.PdbCompactFragment;

public interface MatrixCalculable extends Calculable {

    static PdbCompactFragment pdbChainToCompactFragment(PdbChain chain) {
        return new PdbCompactFragment(String.valueOf(chain.getIdentifier()), chain.getResidues());
    }
}
