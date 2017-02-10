package pl.poznan.put.core.csv;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.poznan.put.core.session.SessionValidator;
import pl.poznan.put.core.session.caches.GenericMatrixCacheManager;
import pl.poznan.put.exceptions.InvalidArgumentException;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.UUID;
import javax.inject.Inject;

@RestController
@RequestMapping("/matrix")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class CsvController {

    private final SessionValidator sessionValidator;
    private final Set<GenericMatrixCacheManager> matrixCacheManagers;
    private final CsvEraser csvEraser;

    @RequestMapping(value = "/csv/{sessionId}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE, method = RequestMethod.GET)
    public final HttpEntity<Resource> csvMatrix(@PathVariable("sessionId") final String sessionId) throws IOException, InvalidArgumentException {
        sessionValidator.validate(sessionId);
        for (GenericMatrixCacheManager manager : matrixCacheManagers) {
            if (manager.exists(UUID.fromString(sessionId)) && manager.getMatrixCache(UUID.fromString(sessionId)).getMatrix() != null) {
                File file = CsvMatrix.builder()
                        .sessionId(UUID.fromString(sessionId))
                        .mtxStructure(manager.getMatrixCache(UUID.fromString(sessionId)).getMatrix())
                        .build()
                        .zipIt();
                csvEraser.add(file);
                return new ResponseEntity<>(new UrlResource(file.toURI()), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
