package pl.poznan.put.core.rnamatrix;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import pl.poznan.put.core.rnamatrix.calculation.StructureCalculation;
import pl.poznan.put.core.rnamatrix.model.Matrix;
import pl.poznan.put.core.rnamatrix.model.MtxStructure;
import pl.poznan.put.core.rnamatrix.specification.StructureSpecification;
import pl.poznan.put.core.structure.EmptyStructureException;
import pl.poznan.put.core.structure.PdbStructure;

@Data
@RequiredArgsConstructor
public abstract class MatrixProvider<T extends Matrix, U> implements MatrixFactory<T, U> {

    private final StructureCalculation<T, U> structureCalculation;

    @Override
    public MtxStructure<T, U> create(PdbStructure structure, StructureSpecification<U> spec) throws EmptyStructureException {
        if (structure != null && !structure.getModels().isEmpty()) {
            return structureCalculation.calculate(structure, spec);
        }
        throw new EmptyStructureException("Structure is empty.");
    }
}
