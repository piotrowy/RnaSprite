package pl.poznan.put.torsionanglesmatrix;

import lombok.RequiredArgsConstructor;
import pl.poznan.put.pdb.analysis.PdbChain;
import pl.poznan.put.pdb.analysis.PdbCompactFragment;
import pl.poznan.put.pdb.analysis.PdbModel;
import pl.poznan.put.pdb.analysis.PdbResidue;
import pl.poznan.put.rna.torsion.RNATorsionAngleType;
import pl.poznan.put.rnamatrix.Calculation;
import pl.poznan.put.rnamatrix.Matrix;
import pl.poznan.put.torsion.MasterTorsionAngleType;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Named
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class TorsionAnglesMatrixCalculation implements Calculation<ResidueInfo, String, AngleData, Set<RNATorsionAngleType>> {

    private static final String INVALID = "invalid";
    private static final String EMPTY = "-";
    private static final String SEPARATOR = "#";
    private final Matrix<ResidueInfo, String, AngleData> matrix;

    @Override
    public Matrix<ResidueInfo, String, AngleData> calculateMatrix(final PdbModel model, final Set<RNATorsionAngleType> angles) {
        if (angles  == null || angles.isEmpty()) {
            model.getChains().stream().forEach(chain -> parseChain(chain, Stream.of(RNATorsionAngleType.values())
                    .flatMap(Stream::of)
                    .collect(Collectors.toSet())));
        } else {
            model.getChains().stream().forEach(chain -> parseChain(chain, angles));
        }
        xLabelsAdd(angles);
        setName(model);
        return matrix;
    }

    private void parseChain(final PdbChain chain, final Set<RNATorsionAngleType> angles) {
        PdbCompactFragment fragment = Calculation.pdbChainToCompactFragment(chain);
        fragment.getResidues().stream().forEach(residue -> {
            this.yLabelAdd(residue);
            parseResidue(fragment, residue, angles);
        });
    }

    private void parseResidue(final PdbCompactFragment fragment, final PdbResidue residue, final Set<RNATorsionAngleType> angles) {
        this.matrix.getData()
                .add(Stream.of(RNATorsionAngleType.values())
                        .flatMap(Stream::of).filter(angles::contains)
                        .map(angle -> setAngleValue(fragment, residue, angle))
                        .collect(Collectors.toList()));
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

    private void setName(PdbModel model) {
        matrix.setName(model.getIdCode() + SEPARATOR + model.getModelNumber());
    }

    private void xLabelsAdd(Set<RNATorsionAngleType> angles) {
        this.matrix.setXLabels(angles.stream().map(RNATorsionAngleType::getShortDisplayName).collect(Collectors.toList()));
    }

    private void yLabelAdd(final PdbResidue residue) {
        this.matrix.getYLabels().add(ResidueInfo.builder()
                .number(residue.getResidueNumber())
                .oneLetterName(String.valueOf(residue.getOneLetterName()))
                .insertionCode(String.valueOf(residue.getInsertionCode()))
                .originalName(residue.getOriginalResidueName())
                .dotBracketRepr("")
                .build());
    }
}
