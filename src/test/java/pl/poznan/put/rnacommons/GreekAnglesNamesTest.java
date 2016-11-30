package pl.poznan.put.tests.rnacommons;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import pl.poznan.put.rnacommons.GreekAnglesNames;

import java.io.IOException;

public class GreekAnglesNamesTest {

    public static final String ALFA="α";
    public static final String BETA="β";
    public static final String GAMMA="γ";
    public static final String DELTA="δ";
    public static final String EPSILON="ε";
    public static final String ZETA="ζ";
    public static final String ETA="η";
    public static final String ETA_PRIM="η'";
    public static final String THETA="θ";
    public static final String CHI="χ";

    @Test
    public void greekAnglesNamesListTest() throws IOException {
        Assertions.assertThat(new GreekAnglesNames().getGreekAngleNamesList())
                .hasSize(17)
                .contains(ALFA, BETA, GAMMA, DELTA, EPSILON, ZETA, ETA, ETA_PRIM, THETA, CHI);
    }
}
