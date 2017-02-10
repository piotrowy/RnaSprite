package pl.poznan.put.rnamatrix;

import pl.poznan.put.rnamatrix.model.Matrix;
import pl.poznan.put.rnamatrix.model.MtxStructure;
import pl.poznan.put.rnamatrix.specification.StructureSpecification;

public interface FilteringProvider<T extends Matrix, U> {

    default MtxStructure<T, U> filter(MtxStructure<T, U> structure, StructureSpecification<U> specification) {
        return structure.filterModels(specification);
    }
}
