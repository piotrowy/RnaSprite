package pl.poznan.put.TorsionAnglesMatrix;

import lombok.Value;

@Value
public class ResidueInfo {

    private final int number;
    private final String originalName;
    private final String oneLetterName;
    private final String insertionCode;
}
