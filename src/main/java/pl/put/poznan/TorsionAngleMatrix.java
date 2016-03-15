/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.put.poznan;

import java.io.StringWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import pl.poznan.put.pdb.analysis.PdbChain;
import pl.poznan.put.pdb.analysis.PdbCompactFragment;
import pl.poznan.put.pdb.analysis.PdbModel;
import pl.poznan.put.pdb.analysis.PdbResidue;
import pl.poznan.put.rna.torsion.RNATorsionAngleType;
import pl.poznan.put.torsion.MasterTorsionAngleType;
import static pl.put.poznan.StructureContainer.getGreekAnglesNames;

/**
 *
 * @author piotrowy
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class TorsionAngleMatrix extends Matrix{

    @XmlElement
    private List<String> angleNames;

    public TorsionAngleMatrix() {
        super();
        this.angleNames = Collections.EMPTY_LIST;
    }

    public TorsionAngleMatrix(StructureContainer strC) {
        this.matrix = this.getTorsionAngles(strC);
        if (matrix != null) {
            angleNames = this.getAngleNamesList();
        }
    }

    /**
     *
     * @param structureList
     * @return
     */
    private List<List<? extends Row>> getTorsionAngles(StructureContainer structureList) {
        if (!structureList.getStructureList().isEmpty()) {
            int index = 0;
            List<List<? extends Row>> matrixMap = new ArrayList<>();
            for (PdbModel model : structureList.getStructureList()) {
                index++;

                for (PdbChain chain : model.getChains()) {
                    PdbCompactFragment fragment = pdbChainToCompactFragment(chain);
                    List<TorsionAngleRow> matrixTA = new ArrayList<>();
                    for (PdbResidue residue : fragment.getResidues()) {
                        TorsionAngleRow row = new TorsionAngleRow(residue.getOriginalResidueName(), residue.getOneLetterName() + "",
                                residue.getResidueNumber() + residue.getInsertionCode() + "", index, chain.toString());
                        for (MasterTorsionAngleType angle : RNATorsionAngleType.values()) {
                            if (fragment.getTorsionAngleValue(residue, angle).getValue().toString().equalsIgnoreCase("invalid")) {
                                row.getRow().add("-");
                            } else {
                                DecimalFormat df2 = new DecimalFormat("#,###,###,##0.00");
                                row.getRow().add(df2.format(fragment.getTorsionAngleValue(residue, angle).getValue().getDegrees()));
                            }
                        }
                        matrixTA.add(row);
                    }
                    matrixMap.add(matrixTA);
                }
            }
            return matrixMap;
        }
        return Collections.emptyList();
    }

    public String getTostionAnglesXls(){
        return null;
    }

    public String getTorsionAnglesUsingVelocity(StructureContainer structureList) {
        List<PdbCompactFragment> compactFragmentsList;
        if (!structureList.getStructureList().isEmpty()) {
            compactFragmentsList = new ArrayList<>();
            for (PdbModel model : structureList.getStructureList()) {
                for (PdbChain chain : model.getChains()) {
                    PdbCompactFragment fragment = pdbChainToCompactFragment(chain);
                    compactFragmentsList.add(fragment);
                    for(PdbResidue residue : fragment.getResidues()){
                        residue.getOriginalResidueName();
                    }
                }
            }
            return generateTorsionAnglesTableView(compactFragmentsList, RNATorsionAngleType.values());
        }
        return null;
    }

    private String generateTorsionAnglesTableView(List<PdbCompactFragment> compactFragmentsList, MasterTorsionAngleType[] torsionAnglesType) {
        VelocityEngine vEnginge = new VelocityEngine();
        vEnginge.init();
        Template viewTemplate;
        viewTemplate = vEnginge.getTemplate("./src/main/webapp/generateTorsionAnglesTableView.vm");
        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("compactFragmentsList", compactFragmentsList);
        velocityContext.put("torsionAnglesType", torsionAnglesType);
        velocityContext.put("greekAnglesNames", getGreekAnglesNames());
        //velocityContext.put("numberTool", new NumberTool());
        StringWriter writer = new StringWriter();
        viewTemplate.merge(velocityContext, writer);
        return writer.toString();
    }

    private List<String> getAngleNamesList() {
        List<String> aNames = new ArrayList<>();
        for (MasterTorsionAngleType angle : RNATorsionAngleType.values()) {
            aNames.add(StructureContainer.getGreekAnglesNames().get(angle.toString().toLowerCase()));
        }
        return aNames;
    }

    /**
     * @return the angleNames
     */
    public List<String> getAngleNames() {
        return angleNames;
    }

    /**
     * @param angleNames the angleNames to set
     */
    public void setAngleNames(List<String> angleNames) {
        this.angleNames = angleNames;
    }
}
