package pl.poznan.put.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.poznan.put.exceptions.PdbDoesNotExistException;
import pl.poznan.put.pdb.PdbParsingException;
import pl.poznan.put.rnacommons.PdbIdsManager;
import pl.poznan.put.session.SessionManager;
import pl.poznan.put.structure.PdbStructure;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class SessionController {

    private final PdbIdsManager pdbIdsManager;
    private final SessionManager sessionManager;

    private static final int PDB_ID_LENGTH = 4;

    @RequestMapping("/pdbId/{id}")
    public final HttpEntity<UUID> sessionId(@PathVariable("id") final String id) throws IOException, PdbParsingException {
        log.info("PdbId {}", id);
        this.validatePdbId(id);
        return new ResponseEntity<>(this.getSession(id), HttpStatus.OK);
    }

    @RequestMapping("refresh-sessions/{ids}")//  http://localhost:8080/refresh-sessions/1,2,3,4,5,6
    public final HttpEntity refreshSessions(@PathVariable("ids") final String[] ids) throws IOException, PdbParsingException {
        refresh(Arrays.asList(ids).stream().map(UUID::fromString).collect(Collectors.toList()));
        return new ResponseEntity(HttpStatus.OK);
    }

    private void refresh(List<UUID> ids) {
        ids.stream().forEach(sessionManager::refreshSession);
    }

    private void  validatePdbId(final String id) {
        if (!this.checkPdbId(id)) {
            log.info("Id {} is not found in the request", id);
            throw new PdbDoesNotExistException(id);
        }
    }

    private boolean checkPdbId(final String id) {
        return id.length() == PDB_ID_LENGTH && pdbIdsManager.doesPdbIdExist(id);
    }

    private UUID getSession(final String id) throws IOException, PdbParsingException {
        return sessionManager.createSession(PdbStructure.fromString(id));
    }
}
