package pl.poznan.put.TorsionAnglesMatrix;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResidueInfo {

    private Integer number;
    private String originalName;
    private String oneLetterName;
    private String insertionCode;
    private String dotBracketRepr;
}
