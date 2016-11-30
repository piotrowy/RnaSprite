package pl.poznan.put.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.poznan.put.exceptions.StructureNotFoundException;
import pl.poznan.put.session.SessionManager;
import pl.poznan.put.structure.PdbStructureChains;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class ChainsListController {

    private final SessionManager sessionManager;
    private final PdbStructureChains pdbStructureChains;

    @RequestMapping(value = "/chains/{sessionId}")
    public final HttpEntity<List<String>> chains(@PathVariable("sessionId") final String sessionId) {
        return sessionManager.getSession(UUID.fromString(sessionId))
                .map(sessionData -> new ResponseEntity<>(pdbStructureChains.loadFromStructure(sessionData.getStructure()), HttpStatus.OK))
                .orElseThrow(() -> {
                    log.debug("Session {} is invalid.", sessionId);
                    return new StructureNotFoundException(sessionId);
                });
    }
}
