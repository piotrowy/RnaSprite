package pl.poznan.put.core.structure.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.poznan.put.pdb.analysis.PdbResidue;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResidueVm {

    private Integer number;
    private String originalName;
    private String oneLetterName;
    private String insertionCode;
    private String dotBracketRepr;

    public static ResidueVm residueInfo(PdbResidue residue) {
        return ResidueVm.builder()
                .number(residue.getResidueNumber())
                .oneLetterName(String.valueOf(residue.getOneLetterName()))
                .insertionCode(String.valueOf(residue.getInsertionCode()))
                .originalName(residue.getOriginalResidueName())
                .dotBracketRepr("")
                .build();
    }
}
