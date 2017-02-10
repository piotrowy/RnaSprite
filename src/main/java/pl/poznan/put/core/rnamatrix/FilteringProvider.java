package pl.poznan.put.core.rnamatrix;

import pl.poznan.put.core.rnamatrix.model.Matrix;
import pl.poznan.put.core.rnamatrix.model.MtxStructure;
import pl.poznan.put.core.rnamatrix.specification.StructureSpecification;

public interface FilteringProvider<T extends Matrix, U> {

    default MtxStructure<T, U> filter(MtxStructure<T, U> structure, StructureSpecification<U> specification) {
        return structure.filterModels(specification);
    }
}
