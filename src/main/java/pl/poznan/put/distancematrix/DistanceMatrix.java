package pl.poznan.put.distancematrix;

import lombok.Builder;
import pl.poznan.put.rnamatrix.model.Matrix;
import pl.poznan.put.structure.AtomsPair;
import pl.poznan.put.structure.ResidueVm;

import java.util.List;

public class DistanceMatrix extends Matrix<ResidueVm, ResidueVm, String> {

    private AtomsPair atomsPair;

    @Builder
    public DistanceMatrix(List<ResidueVm> yLabels, List<ResidueVm> xLabels, List<List<String>> data, AtomsPair atomsPair) {
        super(yLabels, xLabels, data);
        this.atomsPair = atomsPair;
    }
}
