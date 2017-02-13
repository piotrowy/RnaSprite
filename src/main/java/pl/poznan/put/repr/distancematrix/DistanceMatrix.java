package pl.poznan.put.repr.distancematrix;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.poznan.put.core.rnamatrix.model.Matrix;
import pl.poznan.put.core.structure.models.AtomsPair;
import pl.poznan.put.core.structure.models.ResidueVm;

import java.util.List;

public class DistanceMatrix extends Matrix<ResidueVm, ResidueVm, String> {

    @Getter @Setter
    private AtomsPair atomsPair;

    @Builder
    public DistanceMatrix(List<ResidueVm> yLabels, List<ResidueVm> xLabels, List<List<String>> data, AtomsPair atomsPair) {
        super(yLabels, xLabels, data);
        this.atomsPair = atomsPair;
    }
}
