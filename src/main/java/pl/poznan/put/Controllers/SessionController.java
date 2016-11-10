package pl.poznan.put.Controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.poznan.put.RnaCommons.PdbIdContainer;
import pl.poznan.put.Session.SessionManager;
import pl.poznan.put.StructureContainer;

import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import java.util.UUID;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class SessionController {

    private final PdbIdContainer pdbIdContainer;
    private final SessionManager sessionManager;

    private static final int PDB_ID_LENGTH = 4;

    @RequestMapping("/pdbId")
    public final UUID sessionId(@RequestParam(value = "id") final String id) {
        if (this.checkPdbId(id)) {
            return this.getSession(id);
        }
        throw new NotFoundException("Id not found in the request");
    }

    private Boolean checkPdbId(final String id) {
        return id.length() == PDB_ID_LENGTH && pdbIdContainer.isPdbIdExists(id);
    }

    private UUID getSession(final String id) {
        return sessionManager.createSession(StructureContainer.fromString(id));
    }
}
