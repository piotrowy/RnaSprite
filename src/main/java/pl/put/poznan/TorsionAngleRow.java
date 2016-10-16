/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.put.poznan;

import lombok.Getter;
import lombok.Setter;

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
public class TorsionAngleRow extends Row{
    
    @XmlElement
    @Getter @Setter
    private String pdbName;

    @XmlElement
    @Getter @Setter
    private String symbol;

    @XmlElement
    @Getter @Setter
    private String resNo;
    
    public TorsionAngleRow() {
        super();
    }

    public TorsionAngleRow(List<String> row, String pdbName, String symbol, String resNo, int model, String chain) {
        super(row, model, chain);
        this.pdbName = pdbName;
        this.symbol = symbol;
        this.resNo = resNo;
    }

    public TorsionAngleRow(String pdbName, String symbol, String resNo, int model, String chain) {
        super(model, chain);
        this.pdbName = pdbName;
        this.symbol = symbol;
        this.resNo = resNo;
    }
}
