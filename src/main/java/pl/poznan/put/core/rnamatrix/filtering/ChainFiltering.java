package pl.poznan.put.core.rnamatrix.filtering;

import pl.poznan.put.core.rnamatrix.model.Matrix;
import pl.poznan.put.core.rnamatrix.model.MtxChain;

import java.util.List;
import java.util.stream.Collectors;

public interface ChainFiltering<T extends Matrix, U> {

    String getId();

    List<T> getMatrices();

    default MtxChain<T, U> filterMatrix(U specification) {
        return MtxChain.<T, U>builder()
                .id(getId())
                .matrices(getMatrices().stream()
                        .filter(matrix -> matrix instanceof MatrixFiltering)
                        .map(matrix -> (T) ((MatrixFiltering) matrix).filterMatrix(specification))
                        .collect(Collectors.toList()))
                .build();
    }
}
