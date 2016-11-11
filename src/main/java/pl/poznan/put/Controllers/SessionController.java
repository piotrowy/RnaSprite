package pl.poznan.put.Controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.poznan.put.RnaCommons.PdbIdsManager;
import pl.poznan.put.Session.SessionManager;
import pl.poznan.put.Structure.PdbStructure;
import pl.poznan.put.pdb.PdbParsingException;

import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class SessionController {

    private final PdbIdsManager pdbIdsManager;
    private final SessionManager sessionManager;

    private static final int PDB_ID_LENGTH = 4;

    @RequestMapping("/pdbId/{id}")
    public final ResponseEntity<UUID> sessionId(@PathVariable("id") final String id) throws IOException, PdbParsingException {
        log.info("PdbId {}", id);
        if (this.checkPdbId(id)) {
            return new ResponseEntity<>(this.getSession(id), HttpStatus.OK);
        }
        throw new NotFoundException("Id not found in the request");
    }

    private Boolean checkPdbId(final String id) {
        return id.length() == PDB_ID_LENGTH && pdbIdsManager.isPdbIdExists(id);
    }

    private UUID getSession(final String id) throws IOException, PdbParsingException {
        return sessionManager.createSession(PdbStructure.fromString(id));
    }
}
