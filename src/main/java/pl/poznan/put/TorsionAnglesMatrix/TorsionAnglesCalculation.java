package pl.poznan.put.TorsionAnglesMatrix;

import lombok.RequiredArgsConstructor;
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

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class TorsionAnglesCalculation implements MatrixCalculable {

    private final GreekAnglesNames greekAnglesNames;

    private static final String INVALID = "invalid";
    private static final String EMPTY = "-";
    private static final DecimalFormat DECIMAL_FORMAT_2 = new DecimalFormat("#,###,###,##0.00");

    private RnaMatrix<ResidueInfo, String, AngleData> matrix;

    @Override
    public final RnaMatrix calculateMatrix(final PdbModel model) {
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

    private AngleData setAngleValue(PdbCompactFragment fragment, PdbResidue residue, MasterTorsionAngleType angle) {
        if (fragment.getTorsionAngleValue(residue, angle).getValue().toString().equalsIgnoreCase(INVALID)) {
            return new AngleData(EMPTY, EMPTY);
        } else {
            return new AngleData(DECIMAL_FORMAT_2.format(fragment.getTorsionAngleValue(residue, angle).getValue().getDegrees()), EMPTY);
        }
    }

    private void xLabelsAdd() {
        this.matrix.setXLabels(this.greekAnglesNames.getGreekAngleNamesList());
    }

    private void yLabelAdd(PdbResidue residue) {
        this.matrix.getYLabels().add(new ResidueInfo(residue.getResidueNumber(), residue.getOriginalResidueName(),
                String.valueOf(residue.getOneLetterName()), String.valueOf(residue.getInsertionCode())));
    }
}
