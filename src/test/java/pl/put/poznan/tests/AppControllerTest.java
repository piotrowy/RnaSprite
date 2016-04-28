package pl.put.poznan.tests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pl.put.poznan.AppController;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by tzok on 01.03.16.
 * Edited by piotrowy on 28.04.16.
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
    public void testUploadStructureEmptyPDBId() {
        Response response = appController.uploadStructure("");
        Assert.assertEquals("Status code must be 404", 404, response.getStatus());
    }

    @Test
    public void testUploadStructureInvalidPDBId() {
        Response response = appController.uploadStructure("qwerty");
        Assert.assertEquals("Status code must be 404", 404, response.getStatus());
    }

    @Test
    public void testUploadStructureNonExistingPDBId() {
        Response response = appController.uploadStructure("qwer");
        Assert.assertEquals("Status code must be 404", 404, response.getStatus());
    }

    @Test
    public void testUploadStructureValidPDBId() {
        String pattern = "[a-zA-Z0-9]{8}-([a-zA-Z0-9]{4}-){3}[a-zA-Z0-9]{12}";
        Response response = appController.uploadStructure("1EHZ");
        Assert.assertTrue(response.getEntity().toString().matches(pattern));
        Assert.assertEquals("Status code must be 200", 200, response.getStatus());
    }

    @Test
    public void testGetAnglesList() {
        Response response = appController.getAnglesList();
        Assert.assertEquals("Status code must be 200", 200, response.getStatus());
    }

    @Test
    public void testGetTorsionAngles() {
        Response response = appController.uploadStructure("1EHZ");
        response = appController.getTorsionAngles(response.getEntity().toString());
        Assert.assertEquals("Status code must be 200", 200, response.getStatus());
    }

//    @Test
//    public void testGetAnglesFromFileProtein() throws IOException {
//        assertNotNull(appController.uploadStructure(pdb1KTR, null));
//    }
}