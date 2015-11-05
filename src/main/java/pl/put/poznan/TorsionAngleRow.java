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
public class TorsionAngleRow extends Row{
    
    @XmlElement
    private String pdbName;

    @XmlElement
    private String symbol;

    @XmlElement
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

    /**
     * @return the pdbName
     */
    public String getPdbName() {
        return pdbName;
    }

    /**
     * @param pdbName the pdbName to set
     */
    public void setPdbName(String pdbName) {
        this.pdbName = pdbName;
    }

    /**
     * @return the symbol
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * @param symbol the symbol to set
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    /**
     * @return the resNo
     */
    public String getResNo() {
        return resNo;
    }

    /**
     * @param resNo the resNo to set
     */
    public void setResNo(String resNo) {
        this.resNo = resNo;
    }
    
}
