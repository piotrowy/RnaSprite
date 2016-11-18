package pl.poznan.put.TorsionAnglesMatrix;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AngleData {

    private String value;
    private String secondStructureMark;

    public static AngleData buildEmpty() {
        return  new AngleData();
    }
}
