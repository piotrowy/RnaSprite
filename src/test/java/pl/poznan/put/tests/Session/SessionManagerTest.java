package pl.poznan.put.tests.Session;

import org.junit.Assert;
import org.junit.Test;
import pl.poznan.put.Session.SessionManager;
import pl.poznan.put.Structure.PdbStructure;
import pl.poznan.put.Util.ConfigService;
import pl.poznan.put.pdb.PdbParsingException;

import java.io.IOException;
import java.util.UUID;

public class SessionManagerTest {

    private static final String PDB_ID = "1EHZ";

    @Test
    public void createSessionTestValidStructure() throws IOException {
        SessionManager sessionManager = new SessionManager(new ConfigService());
        PdbStructure structure = null;
        try {
            structure = PdbStructure.fromString(PDB_ID);
        } catch (PdbParsingException e) {
            e.printStackTrace();
        }
        UUID id  = sessionManager.createSession(structure);
        Assert.assertNotNull("Shouldn't be null", id);
        Assert.assertEquals("Id string length should be equal to 36", 36, id.toString().length());
        Assert.assertNotNull("SessionData shouldn't be null in sessionMap", SessionManager.getSessionMap().get(id));
    }
}
