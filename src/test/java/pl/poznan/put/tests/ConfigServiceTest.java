package pl.poznan.put.tests;

import org.junit.Before;
import pl.poznan.put.util.ConfigService;

import java.io.IOException;

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
