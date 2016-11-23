package pl.poznan.put.session;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import pl.poznan.put.rnamatrix.Matrix;
import pl.poznan.put.structure.PdbStructure;
import pl.poznan.put.torsionanglesmatrix.AngleData;
import pl.poznan.put.torsionanglesmatrix.ResidueInfo;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class SessionData {

    private final PdbStructure structure;

    private Date lastUseTime;

    private List<Matrix<ResidueInfo, String, AngleData>> torsionAngles;

    private List<Matrix<ResidueInfo, String, AngleData>> distances;
}
