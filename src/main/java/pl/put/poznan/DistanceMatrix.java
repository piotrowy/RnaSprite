/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.put.poznan;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import pl.poznan.put.atom.AtomName;
import pl.poznan.put.pdb.analysis.PdbChain;
import pl.poznan.put.pdb.analysis.PdbCompactFragment;
import pl.poznan.put.pdb.analysis.PdbModel;
import pl.poznan.put.pdb.analysis.PdbResidue;

/**
 *
 * @author piotrowy
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class DistanceMatrix extends Matrix {

	public DistanceMatrix() {
		super();
	}

	public DistanceMatrix(StructureContainer strC, String chainIdentifier,
			String at1, String at2) {
		this.matrix = this.generateDistanceMatrix(strC, chainIdentifier, at1, at2);
	}

    private List<List<? extends Row>> generateDistanceMatrix(StructureContainer strC,
                                                             String chainIdentifier, String at1, String at2) {
        if (!strC.getStructureList().isEmpty()) {
            return strC.getStructureList().stream().map(model -> calculateDistancesInChains(model,
                    model.getModelNumber(), chainIdentifier, at1, at2)).collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

    private List<DistanceMatrixRow> calculateDistancesInChains(PdbModel model, int index, String chainIdentifier,
                                                                         String at1, String at2){
         PdbChain chain = model.getChains().stream().filter(ch -> ch.toString().equalsIgnoreCase(
                 chainIdentifier)).findFirst().get();
         return calculateDistancesBetweenResiduesOuter(index, chain.toString(),
                 pdbChainToCompactFragment(chain), at1, at2);
    }

	private List<DistanceMatrixRow> calculateDistancesBetweenResiduesOuter (int index, String chain,
                                                                            PdbCompactFragment fragment,
																			String at1, String at2) {
		return fragment.getResidues().stream().map(res -> new DistanceMatrixRow(
				calculateDistancesBetweenResiduesInner(fragment, res, at1, at2),
				res.getOneLetterName() + "" + res.getResidueNumber(), index, chain)).collect(Collectors.toList());
	}

	private List<String> calculateDistancesBetweenResiduesInner (PdbCompactFragment fragment, PdbResidue residue,
															String at1, String at2) {
		List <String> listOfDistancesBetweenResidues = new ArrayList<>();
		fragment.getResidues().forEach(res -> listOfDistancesBetweenResidues.add(
				calculateDistance(residue, res, at1, at2)));
		return listOfDistancesBetweenResidues;
	}

	private String calculateDistance (PdbResidue residue, PdbResidue residue1, String at1, String at2){
		DecimalFormat df2 = new DecimalFormat("#,###,###,##0.00");
		return residue1.hasAtom(AtomName.fromString(at2)) && residue.hasAtom(AtomName.fromString(at1))? df2.format(
				residue.findAtom(AtomName.fromString(at1)).distanceTo(residue1.findAtom(
						AtomName.fromString(at2)))) + "" : "";
	}
}


