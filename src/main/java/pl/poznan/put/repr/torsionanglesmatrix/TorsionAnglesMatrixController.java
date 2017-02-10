package pl.poznan.put.repr.torsionanglesmatrix;

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
import pl.poznan.put.core.rnamatrix.specification.ModelSpecification;
import pl.poznan.put.core.rnamatrix.specification.StructureSpecification;
import pl.poznan.put.core.session.SessionManager;
import pl.poznan.put.core.session.SessionValidator;
import pl.poznan.put.core.session.caches.EmptyCacheException;
import pl.poznan.put.core.session.caches.GenericMatrixCacheManager;
import pl.poznan.put.core.structure.EmptyStructureException;
import pl.poznan.put.core.structure.PdbStructure;
import pl.poznan.put.util.exceptions.InvalidArgumentException;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.inject.Inject;

import static java.util.Collections.*;

@Slf4j
@RestController
@RequestMapping("/matrix")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class TorsionAnglesMatrixController {

    private static final String ALL = "ALL";

    private final SessionManager sessionManager;
    private final SessionValidator sessionValidator;
    private final TorsionAnglesMatrixProvider torsionAnglesMatrixProvider;
    private final GenericMatrixCacheManager<TorsionAnglesMatrix, Set<String>> cacheManager;

    @SuppressWarnings("unchecked")
    @PostMapping("/torsion-angles/{sessionId}")
    public final HttpEntity<MtxStructure<TorsionAnglesMatrix, Set<String>>> torsionAnglesMatrix(@PathVariable("sessionId") String sessionId,
                                                                                                @RequestBody StructureSpecification<Set<String>> spec) throws InvalidArgumentException {
        sessionValidator.validate(sessionId);
        try {
            setStructureInSession(UUID.fromString(sessionId), allAnglesStructureSpec(spec));
            MtxStructure<TorsionAnglesMatrix, Set<String>> mtxStructure = torsionAnglesMatrixProvider.filter(cacheManager.getMatrixCache(UUID.fromString(sessionId)).getMatrix(), spec);
            return new ResponseEntity<>(mtxStructure, HttpStatus.OK);
        } catch (Exception ex) {
            log.error("Unable to parse matrix request. {}", ex);
            throw new InvalidArgumentException(ex);
        }
    }

    private void setStructureInSession(UUID sessionId, StructureSpecification<Set<String>> spec) throws EmptyStructureException, EmptyCacheException {
        if (cacheManager.getMatrixCache(sessionId) == null) {
            PdbStructure structure = sessionManager.getStructureCache(sessionId);
            cacheManager.createMatrixCache(torsionAnglesMatrixProvider.create(structure, spec), sessionId);
        }
    }

    private StructureSpecification allAnglesStructureSpec(StructureSpecification<Set<String>> spec) {

        return StructureSpecification.<Set<String>>builder()
                .modelSpecifications(spec.getModelSpecifications()
                        .entrySet()
                        .stream()
                        .collect(Collectors.toMap(Map.Entry::getKey, entry -> allAnglesModelSpec(entry.getValue()))))
                .build();
    }

    private ModelSpecification allAnglesModelSpec(ModelSpecification<Set<String>> spec) {
        return ModelSpecification.<Set<String>>builder()
                .modelNumber(spec.getModelNumber())
                .chainSpecifications(spec.getChainSpecifications()
                        .entrySet()
                        .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> new HashSet(singletonList(ALL)))))
                .build();
    }
}
