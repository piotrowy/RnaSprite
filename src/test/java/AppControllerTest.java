import org.junit.Before;
import org.junit.Test;
import pl.put.poznan.AppController;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertNotNull;

/**
 * Created by tzok on 01.03.16.
 */
public class AppControllerTest {
    private AppController appController;
    private InputStream pdb1KTR;

    @Before
    public void initialize() throws IOException {
        appController = new AppController();
        pdb1KTR = AppControllerTest.class.getResourceAsStream("/1KTR-M.pdb");
    }

    @Test
    public void testGetAnglesFromPDBEmptyPDBId() {
        appController.getAnglesFromPDB("");
    }

    @Test
    public void testGetAnglesFromPDBInvalidPDBId() {
        appController.getAnglesFromPDB("qwerty");
    }

    @Test
    public void testGetAnglesFromFileProtein() throws IOException {
        assertNotNull(appController.getAnglesFromFile(pdb1KTR, null));
    }
}
