package pl.poznan.put.core.rnamatrix.calculation;

import lombok.RequiredArgsConstructor;
import pl.poznan.put.pdb.analysis.PdbChain;
import pl.poznan.put.pdb.analysis.PdbModel;
import pl.poznan.put.core.rnamatrix.model.Matrix;
import pl.poznan.put.core.rnamatrix.model.MtxChain;
import pl.poznan.put.core.rnamatrix.model.MtxModel;
import pl.poznan.put.core.rnamatrix.specification.ModelSpecification;

import java.util.stream.Collectors;
import javax.inject.Inject;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class ModelCalculation<T extends Matrix, U> {

    private final ChainCalculation<T, U> chainCalculation;

    public MtxModel<T, U> calculate(PdbModel pdbModel, ModelSpecification<U> modelSpecification) {
        return MtxModel.<T, U>builder()
                .number(pdbModel.getModelNumber())
                .mtxChains(pdbModel.getChains()
                        .stream()
                        .filter(pdbChain -> modelSpecification.getChainSpecifications()
                                .containsKey(pdbChain.toString()))
                        .map(pdbChain -> calculateChain(pdbChain, modelSpecification))
                        .collect(Collectors.toList()))
                .build();
    }

    private MtxChain<T, U> calculateChain(PdbChain pdbChain, ModelSpecification<U> modelSpecification) {
        return chainCalculation.calculate(pdbChain, modelSpecification.getChainSpecifications().get(pdbChain.toString()));
    }
}
