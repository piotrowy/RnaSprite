package pl.poznan.put.rnamatrix.calculation;

import lombok.RequiredArgsConstructor;
import pl.poznan.put.pdb.analysis.PdbModel;
import pl.poznan.put.rnamatrix.model.Matrix;
import pl.poznan.put.rnamatrix.model.MtxModel;
import pl.poznan.put.rnamatrix.model.MtxStructure;
import pl.poznan.put.rnamatrix.specification.StructureSpecification;
import pl.poznan.put.structure.PdbStructure;

import java.util.stream.Collectors;
import javax.inject.Inject;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class StructureCalculation<T extends Matrix, U> {

    private final ModelCalculation<T, U> modelCalculation;

    public MtxStructure<T, U> calculate(PdbStructure pdbStructure, StructureSpecification<U> structureSpecification) {
        return MtxStructure.<T, U>builder()
                .name(pdbStructure.getName())
                .mtxModels(pdbStructure.getModels()
                        .stream()
                        .filter(pdbModel -> structureSpecification.getModelSpecifications()
                                .keySet()
                                .contains(pdbModel.getModelNumber()))
                        .map(model -> calculateModel(model, structureSpecification))
                        .collect(Collectors.toList()))
                .build();
    }

    private MtxModel<T, U> calculateModel(PdbModel pdbModel, StructureSpecification<U> structureSpecification) {
        return modelCalculation.calculate(pdbModel, structureSpecification.getModelSpecifications().get(pdbModel.getModelNumber()));
    }
}
