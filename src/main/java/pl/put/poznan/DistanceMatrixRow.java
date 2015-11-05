/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.put.poznan;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author piotrowy
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class DistanceMatrixRow extends Row{
    
    @XmlElement
    private String residue;

    public DistanceMatrixRow() {
        super();
    }

    public DistanceMatrixRow(List<String> row, String residue, int model, String chain) {
        super(row, model, chain);
        this.residue = residue;
        
    }

    public DistanceMatrixRow(String residue, int model, String chain) {
        super(model, chain);
        this.residue = residue;
    }

    /**
     * @return the residuesList
     */
    public String getResidue() {
        return residue;
    }

    /**
     * @param residue the residuesList to set
     */
    public void setResidue(String residue) {
        this.residue = residue;
    }
}

