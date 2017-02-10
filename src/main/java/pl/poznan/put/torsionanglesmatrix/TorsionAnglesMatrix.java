package pl.poznan.put.torsionanglesmatrix;

import lombok.Builder;
import pl.poznan.put.rna.torsion.RNATorsionAngleType;
import pl.poznan.put.rnacommons.RnaUtils;
import pl.poznan.put.rnamatrix.filtering.MatrixFiltering;
import pl.poznan.put.rnamatrix.model.Matrix;
import pl.poznan.put.structure.AngleData;
import pl.poznan.put.structure.ResidueVm;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TorsionAnglesMatrix extends Matrix<ResidueVm, String, AngleData>
        implements MatrixFiltering<TorsionAnglesMatrix, Set<String>> {

    @Builder
    public TorsionAnglesMatrix(List<ResidueVm> yLabels, List<String> xLabels, List<List<AngleData>> data) {
        super(yLabels, xLabels, data);
    }

    @Override
    public TorsionAnglesMatrix filterMatrix(Set<String> specification) {
        List<String> anglesShortNames = RnaUtils.mapToRnaTorsionAngleType(specification).stream()
                .map(RNATorsionAngleType::getShortDisplayName)
                .collect(Collectors.toList());
        return TorsionAnglesMatrix.builder()
                .yLabels(getYLabels())
                .xLabels(getXLabels().stream()
                        .filter(anglesShortNames::contains)
                        .collect(Collectors.toList()))
                .data(filterData(anglesShortNames))
                .build();
    }

    private List<List<AngleData>> filterData(List<String> angles) {
        List<Integer> indexes = IntStream.range(0, getXLabels().size())
                .filter(index -> angles.contains(getXLabels().get(index)))
                .mapToObj(Integer::valueOf)
                .collect(Collectors.toList());

        return getData().stream()
                .map(angleDatas -> indexes.stream().map(angleDatas::get)
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());

    }
}

