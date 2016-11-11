package pl.poznan.put.RnaCommons;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;
import pl.poznan.put.constant.Unicode;
import pl.poznan.put.rna.torsion.RNATorsionAngleType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class GreekAngleName {

    /**
     * it maps angle type names to unicode value.
     */
    @Getter
    @Setter
    private Map<String, String> greekNamesMap;


    /**
     * constructor puts all types to map.
     */
    public GreekAngleName() {
        this.greekNamesMap = new HashMap<>();
        this.greekNamesMap.put("alpha", Unicode.ALPHA);
        this.greekNamesMap.put("beta", Unicode.BETA);
        this.greekNamesMap.put("gamma", Unicode.GAMMA);
        this.greekNamesMap.put("delta", Unicode.DELTA);
        this.greekNamesMap.put("epsilon", Unicode.EPSILON);
        this.greekNamesMap.put("zeta", Unicode.ZETA);
        this.greekNamesMap.put("nu0", Unicode.NU0);
        this.greekNamesMap.put("nu1", Unicode.NU1);
        this.greekNamesMap.put("nu2", Unicode.NU2);
        this.greekNamesMap.put("nu3", Unicode.NU3);
        this.greekNamesMap.put("nu4", Unicode.NU4);
        this.greekNamesMap.put("chi", Unicode.CHI);
        this.greekNamesMap.put("eta", Unicode.ETA);
        this.greekNamesMap.put("theta", Unicode.THETA);
        this.greekNamesMap.put("eta_prim", Unicode.ETA_PRIM);
        this.greekNamesMap.put("theta_prim", Unicode.THETA_PRIM);
        this.greekNamesMap.put("pseudophase_pucker", "P");
    }

    /**
     * @return list of angle's greek names
     */
    public final List<String> getGreekAngleNamesList() {
        return Arrays.stream(RNATorsionAngleType.values())
                .map(s -> this.greekNamesMap.get(s.toString().toLowerCase()))
                .collect(Collectors.toList());
    }
}
