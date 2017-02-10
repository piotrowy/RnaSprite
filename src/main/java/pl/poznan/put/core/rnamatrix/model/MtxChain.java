package pl.poznan.put.core.rnamatrix.model;

import lombok.Builder;
import lombok.Value;
import pl.poznan.put.core.rnamatrix.filtering.ChainFiltering;

import java.util.List;

@Value
@Builder
public class MtxChain<T extends Matrix, U> implements ChainFiltering<T, U> {

    private final String id;
    private final List<T> matrices;
}
