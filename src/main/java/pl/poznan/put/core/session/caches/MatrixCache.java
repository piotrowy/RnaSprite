package pl.poznan.put.core.session.caches;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import pl.poznan.put.core.rnamatrix.model.Matrix;
import pl.poznan.put.core.rnamatrix.model.MtxStructure;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class MatrixCache<T extends Matrix, U> {

    private MtxStructure<T, U> matrix;
    private Date lastUseTime;
}
