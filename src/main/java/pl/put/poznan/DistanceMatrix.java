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
			int index = 0;

			List<List<? extends Row>> mtx = new ArrayList<>();
			for (PdbModel model : strC.getStructureList()) {
				index++;
				List<DistanceMatrixRow> distanceMatrixList = new ArrayList<>();
				for (PdbChain chain : model.getChains()) {
					if (chain.toString().equalsIgnoreCase(chainIdentifier)) {
						PdbCompactFragment fragment = pdbChainToCompactFragment(chain);
						for (PdbResidue residue : fragment.getResidues()) {
							List<String> r = new ArrayList<>();
							for (PdbResidue residue1 : fragment.getResidues()) {
								if (residue.hasAtom(AtomName.fromString(at1))
										&& residue1.hasAtom(AtomName
												.fromString(at2))) {
									DecimalFormat df2 = new DecimalFormat(
											"#,###,###,##0.00");
									r.add(df2.format(residue.findAtom(
											AtomName.fromString(at1))
											.distanceTo(
													residue1.findAtom(AtomName
															.fromString(at2))))
											+ "");
								} else {
									r.add("");
								}
							}
							DistanceMatrixRow row = new DistanceMatrixRow(r,
									residue.getOneLetterName() + ""
											+ residue.getResidueNumber(),
									index, chain.toString() + "");
							distanceMatrixList.add(row);
						}
					}
				}
				mtx.add(distanceMatrixList);
			}
			return mtx;
		}
		return Collections.emptyList();
	}
}
