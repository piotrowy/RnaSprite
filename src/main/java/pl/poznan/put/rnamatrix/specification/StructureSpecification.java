package pl.poznan.put.rnamatrix.specification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import pl.poznan.put.util.Copyable;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
public class StructureSpecification<T> implements Copyable<StructureSpecification<T>> {

    private Map<Integer, ModelSpecification<T>> modelSpecifications;

    @Override
    public StructureSpecification<T> copy() {
        return StructureSpecification.<T>builder()
                .modelSpecifications(getModelSpecifications())
                .build();
    }
}
