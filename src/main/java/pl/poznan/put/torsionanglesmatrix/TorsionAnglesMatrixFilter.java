package pl.poznan.put.torsionanglesmatrix;

import lombok.Builder;
import lombok.Data;
import pl.poznan.put.rna.torsion.RNATorsionAngleType;
import pl.poznan.put.rnamatrix.Matrix;

import java.util.Set;

@Data
@Builder
public class TorsionAnglesMatrixFilter {

    private Matrix<ResidueInfo, String, AngleData> matrix;

    public final Matrix<ResidueInfo, String, AngleData> filter(final Set<RNATorsionAngleType> angles) {
        return null;
    }
}
