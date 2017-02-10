package pl.poznan.put.torsionanglesmatrix;

import pl.poznan.put.pdb.analysis.PdbChain;
import pl.poznan.put.pdb.analysis.PdbCompactFragment;
import pl.poznan.put.pdb.analysis.PdbResidue;
import pl.poznan.put.rna.torsion.RNATorsionAngleType;
import pl.poznan.put.rnacommons.RnaUtils;
import pl.poznan.put.rnamatrix.calculation.MatrixCalculation;
import pl.poznan.put.structure.AngleData;
import pl.poznan.put.structure.ResidueVm;
import pl.poznan.put.torsion.MasterTorsionAngleType;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.inject.Named;

@Named
public class TorsionAnglesMatrixCalculation implements MatrixCalculation<TorsionAnglesMatrix, Set<String>> {

    private static final String INVALID = "invalid";
    private static final String EMPTY = "-";

    private List<List<AngleData>> parseFragment(final PdbCompactFragment fragment, final Set<RNATorsionAngleType> angles) {
        return fragment.getResidues()
                .stream()
                .map(residue -> parseResidue(fragment, residue, angles))
                .collect(Collectors.toList());
    }

    private List<AngleData> parseResidue(final PdbCompactFragment fragment, final PdbResidue residue, final Set<RNATorsionAngleType> angles) {
        return angles.stream()
                .map(angle -> setAngleValue(fragment, residue, angle))
                .collect(Collectors.toList());
    }

    private AngleData setAngleValue(final PdbCompactFragment fragment, final PdbResidue residue, final MasterTorsionAngleType angle) {
        if (fragment.getTorsionAngleValue(residue, angle).getValue().toString().equalsIgnoreCase(INVALID)) {
            return AngleData.builder()
                    .value(EMPTY)
                    .build();
        } else {
            return AngleData.builder()
                    .value(DECIMAL_FORMAT_2.format(fragment.getTorsionAngleValue(residue, angle).getValue().getDegrees()))
                    .build();
        }
    }

    private List<String> getXLabels(Set<RNATorsionAngleType> angles) {
        return angles.stream()
                .map(RNATorsionAngleType::getShortDisplayName)
                .collect(Collectors.toList());
    }

    private List<ResidueVm> getYLabels(final PdbCompactFragment fragment) {
        return fragment.getResidues()
                .stream()
                .map(ResidueVm::getResidueInfo)
                .collect(Collectors.toList());
    }

    @Override
    public List<TorsionAnglesMatrix> calculate(PdbChain chain, Set<String> angles) {
        PdbCompactFragment fragment = MatrixCalculation.pdbChainToCompactFragment(chain);
        return Collections.singletonList(TorsionAnglesMatrix.builder()
                .xLabels(getXLabels(RnaUtils.mapToRnaTorsionAngleType(angles)))
                .yLabels(getYLabels(fragment))
                .data(parseFragment(fragment, RnaUtils.mapToRnaTorsionAngleType(angles)))
                .build());
    }


}
