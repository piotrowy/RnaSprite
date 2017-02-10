package pl.poznan.put.rnamatrix;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import pl.poznan.put.rnamatrix.calculation.StructureCalculation;
import pl.poznan.put.rnamatrix.model.Matrix;
import pl.poznan.put.rnamatrix.model.MtxStructure;
import pl.poznan.put.rnamatrix.specification.StructureSpecification;
import pl.poznan.put.structure.EmptyStructureException;
import pl.poznan.put.structure.PdbStructure;

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
