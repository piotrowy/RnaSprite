/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.put.poznan;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import lombok.Getter;
import lombok.Setter;


/**
 *
 * @author piotrowy
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({TorsionAngleRow.class, DistanceMatrixRow.class})
@XmlRootElement
public class Row {

    @XmlElement
    @Getter @Setter
    private List<String> row;

    @XmlElement
    @Getter @Setter
    private int model;

    @XmlElement
    @Getter @Setter
    private String chain;

    public Row() {
        this.row = Collections.EMPTY_LIST;
    }

    public Row(List<String> row, int model, String chain) {
        this(model, chain);
        this.row = row;
    }

    public Row(int model, String chain) {
        this.row = new ArrayList<>();
        this.model = model;
        this.chain = chain;
    }


}
