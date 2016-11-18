package pl.poznan.put.Session;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import pl.poznan.put.RnaMatrix.RnaMatrix;
import pl.poznan.put.Structure.PdbStructure;
import pl.poznan.put.TorsionAnglesMatrix.AngleData;
import pl.poznan.put.TorsionAnglesMatrix.ResidueInfo;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class SessionData {

    private final PdbStructure structure;

    private Date lastUseTime;

    private List<RnaMatrix<ResidueInfo, String, AngleData>> torsionAngles;

    private List<RnaMatrix<ResidueInfo, String, AngleData>> distances;
}
