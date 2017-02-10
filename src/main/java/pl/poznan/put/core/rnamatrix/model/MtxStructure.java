package pl.poznan.put.core.rnamatrix.model;

import lombok.Builder;
import lombok.Value;
import pl.poznan.put.core.rnamatrix.filtering.StructureFiltering;

import java.util.List;

@Value
@Builder
public class MtxStructure<T extends Matrix, U> implements StructureFiltering<T, U> {

    private String name;
    private List<MtxModel<T, U>> mtxModels;
}
