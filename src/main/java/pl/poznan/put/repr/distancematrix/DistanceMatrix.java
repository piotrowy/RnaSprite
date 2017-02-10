package pl.poznan.put.repr.distancematrix;

import lombok.Builder;
import pl.poznan.put.core.rnamatrix.model.Matrix;
import pl.poznan.put.core.structure.AtomsPair;
import pl.poznan.put.core.structure.ResidueVm;

import java.util.List;

public class DistanceMatrix extends Matrix<ResidueVm, ResidueVm, String> {

    private AtomsPair atomsPair;

    @Builder
    public DistanceMatrix(List<ResidueVm> yLabels, List<ResidueVm> xLabels, List<List<String>> data, AtomsPair atomsPair) {
        super(yLabels, xLabels, data);
        this.atomsPair = atomsPair;
    }
}
