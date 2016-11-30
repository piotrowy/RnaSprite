package pl.poznan.put.torsionanglesmatrix;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AngleData {

    private String value;

    public static AngleData buildEmpty() {
        return  new AngleData();
    }
}
