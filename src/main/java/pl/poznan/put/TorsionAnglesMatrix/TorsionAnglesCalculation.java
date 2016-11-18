package pl.poznan.put.TorsionAnglesMatrix;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import pl.poznan.put.RnaCommons.GreekAnglesNames;
import pl.poznan.put.RnaMatrix.MatrixCalculable;
import pl.poznan.put.RnaMatrix.RnaMatrix;
import pl.poznan.put.pdb.analysis.PdbChain;
import pl.poznan.put.pdb.analysis.PdbCompactFragment;
import pl.poznan.put.pdb.analysis.PdbModel;
import pl.poznan.put.pdb.analysis.PdbResidue;
import pl.poznan.put.rna.torsion.RNATorsionAngleType;
import pl.poznan.put.torsion.MasterTorsionAngleType;

import javax.inject.Inject;
import java.text.DecimalFormat;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class TorsionAnglesCalculation implements MatrixCalculable<ResidueInfo, String, AngleData> {

    private final GreekAnglesNames greekAnglesNames;
    private final RnaMatrix<ResidueInfo, String, AngleData> matrix;

    private static final String INVALID = "invalid";
    private static final String EMPTY = "-";

    private static final DecimalFormat DECIMAL_FORMAT_2 = new DecimalFormat("#,###,###,##0.00");

    @Override
    public RnaMatrix<ResidueInfo, String, AngleData> calculateMatrix(final PdbModel model) {
        model.getChains().stream().forEach(this::parseChain);
        this.xLabelsAdd();

        return this.matrix;
    }

    private void parseChain(final PdbChain chain) {
        PdbCompactFragment fragment = MatrixCalculable.pdbChainToCompactFragment(chain);
        fragment.getResidues().stream().forEach(residue -> {
            this.yLabelAdd(residue);
            parseResidue(fragment, residue);
        });
    }

    private void parseResidue(final PdbCompactFragment fragment, final PdbResidue residue) {
        this.matrix.getData()
                .add(Stream.of(RNATorsionAngleType.values())
                        .flatMap(Stream::of)
                        .map(angle -> setAngleValue(fragment, residue, angle))
                        .collect(Collectors.toList()));
    }

    private AngleData setAngleValue(final PdbCompactFragment fragment, final PdbResidue residue, final MasterTorsionAngleType angle) {
        if (fragment.getTorsionAngleValue(residue, angle).getValue().toString().equalsIgnoreCase(INVALID)) {
            return AngleData.builder()
                    .value(EMPTY)
                    .secondStructureMark(EMPTY)
                    .build();
        } else {
            return AngleData.builder()
                    .value(DECIMAL_FORMAT_2.format(fragment.getTorsionAngleValue(residue, angle).getValue().getDegrees()))
                    .secondStructureMark(EMPTY)
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
                .insertionCode("".equals(String.valueOf(residue.getInsertionCode())) ? null : String.valueOf(residue.getInsertionCode()))
                .originalName(residue.getOriginalResidueName())
                .dotBracketRepr("")
                .build());
    }
}
