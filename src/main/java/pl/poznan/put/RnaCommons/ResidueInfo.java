package pl.poznan.put.RnaCommons;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResidueInfo {

    /**
     * pdbName is a pdb name of a residue.
     */
    private String pdbName;

    /**
     * Residue symbol.
     */
    private String symbol;

    /**
     * Residue number;
     */
    private String resNo;

    /**
     * Canonical structure extraction.
     */
    private String dotBracketRepr;
}
