package pl.put.poznan.RnaMatrix;

import lombok.Data;

/**
 * Created by piotrowy on 19.10.2016.
 */
public @Data class ResidueInfo {

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
}
