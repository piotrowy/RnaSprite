package pl.poznan.put.core.rnamatrix.specification;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ChainSpecification<T> {

    private String chainId;
    private T specification;
}
