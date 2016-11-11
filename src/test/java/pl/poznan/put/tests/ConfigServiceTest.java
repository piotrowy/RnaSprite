package pl.poznan.put.tests;

import org.junit.Before;
import pl.poznan.put.Util.ConfigService;

import java.io.IOException;

/**
 * Created by piotrowy on 24.05.2016.
 */
public class ConfigServiceTest {
    private ConfigService cf;

    @Before
    public void initialize() throws IOException {
        cf = new ConfigService();
    }

//    @Test
//    public void loadCurrentPdbListUrlTest() {
//        Assert.assertTrue(cf.currentPdbListUrl().equals("http://www.rcsb.org/pdb/rest/getCurrent"));
//    }
//
//    @Test
//    public void loadObsoletePdbListUrlTest() {
//        Assert.assertTrue(cf.obsoletePdbListUrl().equals("http://www.rcsb.org/pdb/rest/getObsolete"));
//    }

}
