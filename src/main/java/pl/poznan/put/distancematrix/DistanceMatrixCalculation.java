package pl.poznan.put.distancematrix;

import lombok.RequiredArgsConstructor;
import pl.poznan.put.atom.AtomName;
import pl.poznan.put.pdb.analysis.PdbModel;
import pl.poznan.put.pdb.analysis.PdbResidue;
import pl.poznan.put.rnamatrix.Calculation;
import pl.poznan.put.rnamatrix.Matrix;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Named
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class DistanceMatrixCalculation implements Calculation<String, String, String, String> {

    private final Matrix<String, String, String> matrix;

    public Matrix<String, String, String> calculateMatrix(PdbModel model, String chainArg, String... args) {
        matrix.setXLabels(getLabels(model, chainArg));
        matrix.setYLabels(matrix.getXLabels());
        //matrix.setData(model.getChains().stream().map(chain -> distanceBetweenAtomsInResidues(chain.getResidues(), atom1, atom2)));
        return matrix;
    }

    private List<List<String>> distanceBetweenAtomsInResidues(List<PdbResidue> residues, String atom1, String atom2) {
        return IntStream.range(0, residues.size())
                .mapToObj(outerIndex -> IntStream.range(0, outerIndex)
                        .mapToObj(innerIndex -> DECIMAL_FORMAT_2.format(residues.get(outerIndex).findAtom(AtomName.fromString(atom1))
                                .distanceTo(residues.get(innerIndex).findAtom(AtomName.fromString(atom2)))))
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    private List<String> getLabels(PdbModel model, String chainArg) {
        return model.getChains().stream()
                .filter(chain -> chainArg.equalsIgnoreCase(chain.toString()))
                .findFirst()
                .get()
                .getResidues().stream()
                .map(PdbResidue::getOriginalResidueName)
                .collect(Collectors.toList());
    }

    @Override
    public Matrix<String, String, String> calculateMatrix(PdbModel model, String arg) {
        return null;
    }
}
