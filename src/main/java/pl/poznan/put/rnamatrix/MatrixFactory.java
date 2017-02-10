package pl.poznan.put.rnamatrix;

import pl.poznan.put.rnamatrix.model.Matrix;
import pl.poznan.put.rnamatrix.model.MtxStructure;
import pl.poznan.put.rnamatrix.specification.StructureSpecification;
import pl.poznan.put.structure.EmptyStructureException;
import pl.poznan.put.structure.PdbStructure;

public interface MatrixFactory<T extends Matrix, U> {

    MtxStructure<T, U> create(PdbStructure structure, StructureSpecification<U> specification) throws EmptyStructureException;
}
