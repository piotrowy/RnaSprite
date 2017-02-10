package pl.poznan.put.rnamatrix.specification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import pl.poznan.put.util.Copyable;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
public class ModelSpecification<T> implements Copyable<ModelSpecification<T>> {

    private Integer modelNumber;
    private Map<String, T> chainSpecifications;

    @Override
    public ModelSpecification<T> copy() {
        return ModelSpecification.<T>builder()
                .modelNumber(getModelNumber())
                .chainSpecifications(getChainSpecifications())
                .build();
    }
}
