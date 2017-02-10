package pl.poznan.put.distancematrix;

import lombok.Builder;
import lombok.Data;
import pl.poznan.put.structure.AtomsPair;

import java.util.List;

@Data
@Builder
public class ResiduesAtomsSpecification {

    private List<Integer> residues;
    private AtomsPair atoms;
}
