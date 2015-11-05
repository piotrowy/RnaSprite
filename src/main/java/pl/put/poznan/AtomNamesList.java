/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.put.poznan;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import pl.poznan.put.atom.AtomName;
import pl.poznan.put.rna.Phosphate;
import pl.poznan.put.rna.Ribose;
import pl.poznan.put.rna.base.Adenine;
import pl.poznan.put.rna.base.Cytosine;
import pl.poznan.put.rna.base.Guanine;
import pl.poznan.put.rna.base.Uracil;

/**
 *
 * @author piotrowy
 */
@XmlRootElement
public class AtomNamesList {
    
    @XmlElement
    private List <String> list;

    public AtomNamesList(){
        Set<AtomName> atomNames = new LinkedHashSet<>();
        atomNames.addAll(Phosphate.getInstance().getAtoms());
        atomNames.addAll(Ribose.getInstance().getAtoms());
        atomNames.addAll(Adenine.getInstance().getAtoms());
        atomNames.addAll(Guanine.getInstance().getAtoms());
        atomNames.addAll(Cytosine.getInstance().getAtoms());
        atomNames.addAll(Uracil.getInstance().getAtoms());
        List<String> namesList = new ArrayList<>();
        for(AtomName name : atomNames){
            namesList.add(name.getName());
        }
        this.list = namesList;
    }
    
    /**
     * @return the list
     */
    public List <String> getList() {
        return list;
    }

    /**
     * @param list the list to set
     */
    public void setList(List <String> list) {
        this.list = list;
    }
}

