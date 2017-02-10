package pl.poznan.put.repr.distancematrix;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import pl.poznan.put.core.structure.AtomsPair;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ResiduesAtomsSpecification {

    private List<Integer> residues;
    private AtomsPair atoms;
}
