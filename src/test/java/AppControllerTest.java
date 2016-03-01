import org.junit.Before;
import org.junit.Test;
import pl.put.poznan.AppController;

/**
 * Created by tzok on 01.03.16.
 */
public class AppControllerTest {
    private AppController appController;

    @Before
    public void initialize() {
        appController = new AppController();
    }

    @Test
    public void testGetAnglesFromPDBEmptyPDBId() {
        appController.getAnglesFromPDB("");
    }

    @Test
    public void testGetAnglesFromPDBInvalidPDBId() {
        appController.getAnglesFromPDB("qwerty");
    }
}
