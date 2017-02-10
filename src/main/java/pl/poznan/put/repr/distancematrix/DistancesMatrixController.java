package pl.poznan.put.repr.distancematrix;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.poznan.put.core.rnamatrix.model.MtxStructure;
import pl.poznan.put.core.rnamatrix.specification.StructureSpecification;
import pl.poznan.put.core.session.SessionManager;
import pl.poznan.put.core.session.SessionValidator;
import pl.poznan.put.core.session.caches.EmptyCacheException;
import pl.poznan.put.core.session.caches.GenericMatrixCacheManager;
import pl.poznan.put.core.structure.EmptyStructureException;
import pl.poznan.put.core.structure.PdbStructure;
import pl.poznan.put.util.exceptions.InvalidArgumentException;

import java.util.UUID;
import javax.inject.Inject;

@Slf4j
@RestController
@RequestMapping("/matrix")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class DistancesMatrixController {

    private final SessionManager sessionManager;
    private final SessionValidator sessionValidator;
    private final DistanceMatrixProvider distanceMatrixProvider;
    private final GenericMatrixCacheManager<DistanceMatrix, ResiduesAtomsSpecification> cacheManager;

    @SuppressWarnings("unchecked")
    @PostMapping("/distances/{sessionId}")
    public final HttpEntity<MtxStructure<DistanceMatrix, ResiduesAtomsSpecification>> torsionAnglesMatrix(@PathVariable("sessionId") String sessionId,
                                               @RequestBody StructureSpecification<ResiduesAtomsSpecification> spec) throws InvalidArgumentException {
        sessionValidator.validate(sessionId);
        try {
            setStructureInSession(UUID.fromString(sessionId), spec);
            return new ResponseEntity<>(cacheManager.getMatrixCache(UUID.fromString(sessionId)).getMatrix(), HttpStatus.OK);
        } catch (Exception ex) {
            log.error("Unable to parse matrix request. {}", ex);
            throw new InvalidArgumentException(ex);
        }
    }

    private void setStructureInSession(UUID sessionId, StructureSpecification<ResiduesAtomsSpecification> spec)
            throws EmptyStructureException, EmptyCacheException {
        if (cacheManager.getMatrixCache(sessionId) == null) {
            PdbStructure structure = sessionManager.getStructureCache(sessionId);
            cacheManager.createMatrixCache(distanceMatrixProvider.create(structure, spec), sessionId);
        }
    }
}
