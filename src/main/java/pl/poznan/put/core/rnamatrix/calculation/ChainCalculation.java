package pl.poznan.put.core.rnamatrix.calculation;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import pl.poznan.put.pdb.analysis.PdbChain;
import pl.poznan.put.core.rnamatrix.model.Matrix;
import pl.poznan.put.core.rnamatrix.model.MtxChain;

import javax.inject.Inject;

@Data
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class ChainCalculation<T extends Matrix, U> {

    private final MatrixCalculation<T, U> matrixCalculation;

    public MtxChain<T, U> calculate(PdbChain pdbChain, U specification) {
        return MtxChain.<T, U>builder()
                .id(pdbChain.toString())
                .matrices(matrixCalculation.calculate(pdbChain, specification))
                .build();
    }
}
