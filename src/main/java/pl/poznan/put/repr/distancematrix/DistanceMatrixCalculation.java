package pl.poznan.put.repr.distancematrix;

import pl.poznan.put.atom.AtomName;
import pl.poznan.put.pdb.analysis.PdbChain;
import pl.poznan.put.pdb.analysis.PdbResidue;
import pl.poznan.put.core.rnamatrix.calculation.MatrixCalculation;
import pl.poznan.put.core.structure.AtomsPair;
import pl.poznan.put.core.structure.ResidueVm;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.inject.Named;

@Named
public class DistanceMatrixCalculation implements MatrixCalculation<DistanceMatrix, ResiduesAtomsSpecification> {

    private DistanceMatrix calculateDistancesForAtoms(AtomsPair atomsPair, List<PdbResidue> residues) {
        List<ResidueVm> residueVms = residues
                .stream()
                .map(ResidueVm::getResidueInfo)
                .collect(Collectors.toList());
        return DistanceMatrix.builder()
                .atomsPair(atomsPair)
                .xLabels(residueVms)
                .yLabels(residueVms)
                .data(distanceBetweenTwoAtomsInResidues(residues, atomsPair))
                .build();
    }

    private List<List<String>> distanceBetweenTwoAtomsInResidues(List<PdbResidue> residues, AtomsPair atoms) {
        return IntStream.range(0, residues.size())
                .mapToObj(outerIndex -> IntStream.range(0, outerIndex)
                        .mapToObj(innerIndex -> DECIMAL_FORMAT_2.format(residues
                                .get(outerIndex)
                                .findAtom(AtomName.fromString(atoms.getFirst()))
                                .distanceTo(residues.get(innerIndex).findAtom(AtomName.fromString(atoms.getSecond())))))
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    @Override
    public List<DistanceMatrix> calculate(PdbChain chain, ResiduesAtomsSpecification specification) {
        return Collections.singletonList(calculateDistancesForAtoms(specification.getAtoms(), chain.getResidues()
                .stream()
                .filter(residue -> specification.getResidues().contains(residue.getResidueNumber()))
                .collect(Collectors.toList())));
    }
}
