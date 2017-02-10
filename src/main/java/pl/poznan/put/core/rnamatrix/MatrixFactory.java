package pl.poznan.put.core.rnamatrix;

import pl.poznan.put.core.rnamatrix.model.Matrix;
import pl.poznan.put.core.rnamatrix.model.MtxStructure;
import pl.poznan.put.core.rnamatrix.specification.StructureSpecification;
import pl.poznan.put.core.structure.EmptyStructureException;
import pl.poznan.put.core.structure.PdbStructure;

public interface MatrixFactory<T extends Matrix, U> {

    MtxStructure<T, U> create(PdbStructure structure, StructureSpecification<U> specification) throws EmptyStructureException;
}
