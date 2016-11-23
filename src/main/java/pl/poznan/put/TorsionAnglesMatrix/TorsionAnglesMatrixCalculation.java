package pl.poznan.put.torsionanglesmatrix;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.poznan.put.pdb.analysis.PdbChain;
import pl.poznan.put.pdb.analysis.PdbCompactFragment;
import pl.poznan.put.pdb.analysis.PdbModel;
import pl.poznan.put.pdb.analysis.PdbResidue;
import pl.poznan.put.rna.torsion.RNATorsionAngleType;
import pl.poznan.put.rnacommons.GreekAnglesNames;
import pl.poznan.put.rnamatrix.Calculation;
import pl.poznan.put.rnamatrix.Matrix;
import pl.poznan.put.rnamatrix.SpecificCalculation;
import pl.poznan.put.torsion.MasterTorsionAngleType;

import javax.inject.Inject;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class TorsionAnglesMatrixCalculation implements SpecificCalculation<ResidueInfo, String, AngleData, RNATorsionAngleType> {

    private final GreekAnglesNames greekAnglesNames;
    private final Matrix<ResidueInfo, String, AngleData> matrix;

    private static final String INVALID = "invalid";
    private static final String EMPTY = "-";

    private static final DecimalFormat DECIMAL_FORMAT_2 = new DecimalFormat("#,###,###,##0.00");

    @Override
    public final Matrix<ResidueInfo, String, AngleData> calculateMatrix(final PdbModel model) {
        model.getChains().stream().forEach(chain -> parseChain(chain, Arrays.asList(RNATorsionAngleType.values())));
        this.xLabelsAdd();
        this.matrix.setName(model.getIdCode() + model.getModelNumber());
        return this.matrix;
    }

    @Override
    public final Matrix<ResidueInfo, String, AngleData> calculateSpecificMatrix(final PdbModel model, final RNATorsionAngleType... args) {
        model.getChains().stream().forEach(chain -> parseChain(chain, Arrays.asList(args)));
        this.xLabelsAdd();

        return this.matrix;
    }

    private void parseChain(final PdbChain chain, final List<RNATorsionAngleType> torsionAngleTypeList) {
        PdbCompactFragment fragment = Calculation.pdbChainToCompactFragment(chain);
        fragment.getResidues().stream().forEach(residue -> {
            this.yLabelAdd(residue);
            parseResidue(fragment, residue, torsionAngleTypeList);
        });
    }

    private void parseResidue(final PdbCompactFragment fragment, final PdbResidue residue, final List<RNATorsionAngleType> torsionAngleTypeList) {
        this.matrix.getData()
                .add(Stream.of(RNATorsionAngleType.values())
                        .flatMap(Stream::of).filter(torsionAngleTypeList::contains)
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

    private void xLabelsAdd() {
        this.matrix.setXLabels(this.greekAnglesNames.getGreekAngleNamesList());
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
