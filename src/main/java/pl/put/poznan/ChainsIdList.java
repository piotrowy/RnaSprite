/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.put.poznan;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import pl.poznan.put.pdb.analysis.PdbChain;
import pl.poznan.put.pdb.analysis.PdbModel;

/**
 *
 * @author piotrowy
 */
@XmlRootElement
public class ChainsIdList {

    @XmlElement
    private List <String> list;
    
    public ChainsIdList() {
        
    }
    
    public ChainsIdList(StructureContainer strC) {
        if (!strC.getStructureList().isEmpty()) {
            int index = 0;
            list = new ArrayList<>();
            for (PdbModel model : strC.getStructureList()) {
                index++;
                model.getChains().stream().filter(chain -> !this.list.contains(chain.toString())).forEach(ch -> {
                    this.list.add(ch.toString());
                });
            }
        }
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
    public void setChainsList(List <String> list) {
        this.list = list;
    }
    
}
