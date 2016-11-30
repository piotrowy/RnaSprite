package pl.poznan.put.tests.rnacommons;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import pl.poznan.put.rnacommons.AtomNamesList;

import java.io.IOException;

public class AtomsNamesListTest {

    public static final String EX1="O3'";
    public static final String EX2="O2";
    public static final String EX3="H6";
    public static final String ANTI_EX1="H9";
    public static final String ANTI_EX2="O2''";
    public static final String ANTI_EX3="";

    @Test
    public void getAtomsNamesListTest() throws IOException {
        Assertions.assertThat(new AtomNamesList().getList())
                .hasSize(46)
                .contains(EX1, EX2, EX3)
                .doesNotContain(ANTI_EX1, ANTI_EX2, ANTI_EX3);
    }
}
