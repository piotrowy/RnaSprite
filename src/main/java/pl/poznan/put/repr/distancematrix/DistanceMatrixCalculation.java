package pl.poznan.put.repr.distancematrix;

import pl.poznan.put.atom.AtomName;
import pl.poznan.put.core.rnamatrix.calculation.MatrixCalculation;
import pl.poznan.put.core.structure.models.AtomsPair;
import pl.poznan.put.core.structure.models.ResidueVm;
import pl.poznan.put.pdb.analysis.PdbChain;
import pl.poznan.put.pdb.analysis.PdbResidue;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Named;

@Named
public class DistanceMatrixCalculation implements MatrixCalculation<DistanceMatrix, ResiduesAtomsSpecification> {

    private DistanceMatrix calculateDistancesForAtoms(AtomsPair atomsPair, List<PdbResidue> residues) {
        List<ResidueVm> residueVms = residues
                .stream()
                .map(ResidueVm::residueInfo)
                .collect(Collectors.toList());
        return DistanceMatrix.builder()
                .atomsPair(atomsPair)
                .xLabels(residueVms)
                .yLabels(residueVms)
                .data(distanceBetweenTwoAtomsInResidues(residues, atomsPair))
                .build();
    }

    private List<List<String>> distanceBetweenTwoAtomsInResidues(List<PdbResidue> residues, AtomsPair atoms) {
        return residues.stream()
                .map(outerResidue -> residues.stream()
                        .map(innerResidue -> outerResidue.hasAtom(AtomName.fromString(atoms.getFirst()))
                                && innerResidue.hasAtom(AtomName.fromString(atoms.getSecond()))
                                ? DECIMAL_FORMAT_2.format(outerResidue.findAtom(AtomName.fromString(atoms.getFirst()))
                                .distanceTo(innerResidue.findAtom(AtomName.fromString(atoms.getSecond())))) : "-")
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    @Override
    public List<DistanceMatrix> calculate(PdbChain chain, ResiduesAtomsSpecification specification) {
        if (specification.getResidues().isEmpty()) {
            return Collections.singletonList(calculateDistancesForAtoms(specification.getAtoms(), chain.getResidues()));
        }
        return Collections.singletonList(calculateDistancesForAtoms(specification.getAtoms(), chain.getResidues()
                .stream()
                .filter(residue -> specification.getResidues().contains(residue.getResidueNumber()))
                .collect(Collectors.toList())));
    }
}
