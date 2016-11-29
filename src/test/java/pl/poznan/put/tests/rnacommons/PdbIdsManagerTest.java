package pl.poznan.put.tests.rnacommons;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import pl.poznan.put.rnacommons.PdbIdsManager;

import java.io.IOException;

public class PdbIdsManagerTest {

    private static final String OBSOLETE_EX_1 = "1ABH";
    private static final String OBSOLETE_EX_2 = "1VUE";
    private static final String OBSOLETE_EX_3 = "4LQ7";

    private static final String CURRENT_EX_1 = "1EHZ";
    private static final String CURRENT_EX_2 = "1FK0";
    private static final String CURRENT_EX_3 = "5D7Q";

    private static final String FAKE_PDB_ID = "A1234";

    private PdbIdsManager pdbIdsManager;

    @Before
    public void init() throws IOException {
        pdbIdsManager = new PdbIdsManager();
    }

    @Test
    public void allPdbIdsTest() {
        Assertions.assertThat(pdbIdsManager.allPdbIds())
                .as("check if pdb ids set has correct size and contains chosen ids.")
                .hasSize(pdbIdsManager.obsoletePdbIds().size() + pdbIdsManager.currentPdbIds().size())
                .contains(CURRENT_EX_1, CURRENT_EX_2, CURRENT_EX_3, OBSOLETE_EX_1, OBSOLETE_EX_2, OBSOLETE_EX_3);
    }

    @Test
    public void doesPdbIdExistTest() {
        Assertions.assertThat(pdbIdsManager.doesPdbIdExist(CURRENT_EX_1))
                .as("check if id exists.")
                .isTrue();
        Assertions.assertThat(pdbIdsManager.doesPdbIdExist(FAKE_PDB_ID))
                .as("check if id does not exist.")
                .isFalse();
    }

    @Test
    public void currentPdbIdsTest() {
        Assertions.assertThat(pdbIdsManager.currentPdbIds())
                .as("check if current pdb ids set has correct size and contains chosen ids.")
                .contains(CURRENT_EX_1, CURRENT_EX_2, CURRENT_EX_3)
                .doesNotContain(OBSOLETE_EX_1, OBSOLETE_EX_2, OBSOLETE_EX_3);
    }

    @Test
    public void obsoletePdbIdsTest() {
        Assertions.assertThat(pdbIdsManager.obsoletePdbIds())
                .as("check if obsolete pdb ids set has correct size and contains chosen ids.")
                .contains(OBSOLETE_EX_1, OBSOLETE_EX_2, OBSOLETE_EX_3)
                .doesNotContain(CURRENT_EX_1, CURRENT_EX_2, CURRENT_EX_3);
    }
}
