package pl.poznan.put.rnacommons;

import lombok.experimental.UtilityClass;
import pl.poznan.put.rna.torsion.RNATorsionAngleType;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class RnaUtils {

    public static final String ALL = "ALL";

    public static Set<RNATorsionAngleType> mapToRnaTorsionAngleType(Set<String> types) {
        if (types.contains(ALL) || types.isEmpty()) {
            return new LinkedHashSet<>(Arrays.asList(RNATorsionAngleType.values()));
        } else {
            return types.stream()
                    .map(String::toUpperCase)
                    .map(RNATorsionAngleType::valueOf)
                    .collect(Collectors.toSet());
        }
    }
}
