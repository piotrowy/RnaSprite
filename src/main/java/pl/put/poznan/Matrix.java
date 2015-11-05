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
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import pl.poznan.put.pdb.analysis.PdbChain;
import pl.poznan.put.pdb.analysis.PdbCompactFragment;
import pl.poznan.put.pdb.analysis.PdbResidue;

/**
 *
 * @author piotrowy
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Matrix {
    
    @XmlJavaTypeAdapter(ListAdapter.class)
    @XmlElement
    protected List<List<? extends Row>> matrix;
    
    public Matrix(){
        
    }

        
    protected PdbCompactFragment pdbChainToCompactFragment(PdbChain chain) {
        List<PdbResidue> residues = chain.getResidues();
        String name = String.valueOf(chain.getIdentifier());
        return new PdbCompactFragment(name, residues);
    }
    
    /**
     * @return the matrix
     */
    public List<List<? extends Row>> getMatrix() {
        return matrix;
    }

    /**
     * @param matrix the matrix to set
     */
    public void setMatrix(List<List<? extends Row>> matrix) {
        this.matrix = matrix;
    }
    
}
