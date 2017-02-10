package pl.poznan.put.core.session;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.poznan.put.core.structure.PdbStructure;
import pl.poznan.put.pdb.PdbAtomLine;
import pl.poznan.put.pdb.analysis.PdbChain;
import pl.poznan.put.pdb.analysis.PdbModel;
import pl.poznan.put.pdb.analysis.PdbResidue;
import pl.poznan.put.exceptions.InvalidArgumentException;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.inject.Inject;

@Slf4j
@RestController
@RequestMapping("/structure")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class StructureController {

    private static final String EXCEPTION_MESSAGE = "Wrong filtering data.";
    private final SessionValidator sessionValidator;
    private final SessionManager sessionManager;

    @GetMapping("/atoms/{sessionId}/{model}/{chain}")
    public HttpEntity<Set<String>> getAtoms(@PathVariable("sessionId") String sessionId, @PathVariable("model") Integer model,
                                            @PathVariable("chain") String chain) throws InvalidArgumentException {
        sessionValidator.validate(sessionId);
        try {
            return new ResponseEntity<>(extractChain(extractModel(sessionManager.getStructureCache(UUID.fromString(sessionId)), model).get(), chain)
                    .map(PdbChain::getResidues)
                    .get()
                    .stream()
                    .map(PdbResidue::getAtoms)
                    .flatMap(List::stream)
                    .map(PdbAtomLine::getAtomName)
                    .collect(Collectors.toSet()), HttpStatus.OK);
        } catch (Exception ex) {
            throw new InvalidArgumentException(EXCEPTION_MESSAGE);
        }
    }

    @GetMapping("/residues/{sessionId}/{model}/{chain}")
    public HttpEntity<Set<Integer>> getResidues(@PathVariable("sessionId") String sessionId, @PathVariable("model") Integer model,
                                                @PathVariable("chain") String chain) throws InvalidArgumentException {
        sessionValidator.validate(sessionId);
        try {
            return new ResponseEntity<>(extractChain(extractModel(sessionManager.getStructureCache(UUID.fromString(sessionId)), model).get(), chain)
                    .map(PdbChain::getResidues)
                    .get()
                    .stream()
                    .map(PdbResidue::getResidueNumber)
                    .collect(Collectors.toSet()), HttpStatus.OK);
        } catch (Exception ex) {
            log.error("Eror while extracting residues from structure. {}", ex);
            throw new InvalidArgumentException(EXCEPTION_MESSAGE);
        }
    }

    @GetMapping("/chains/{sessionId}/{model}")
    public HttpEntity<Set<String>> getChains(@PathVariable("sessionId") String sessionId, @PathVariable("model") Integer model)
            throws InvalidArgumentException {
        sessionValidator.validate(sessionId);
        Set<String> chains = extractModel(sessionManager.getStructureCache(UUID.fromString(sessionId)), model)
                .map(pdbModel -> pdbModel.getChains()
                        .stream()
                        .map(PdbChain::toString)
                        .collect(Collectors.toSet()))
                .orElseThrow(() -> new InvalidArgumentException(EXCEPTION_MESSAGE));
        return new ResponseEntity<>(chains, HttpStatus.OK);
    }

    @GetMapping("/models/{sessionId}")
    public HttpEntity<Set<Integer>> getModels(@PathVariable("sessionId") String sessionId) throws InvalidArgumentException {
        sessionValidator.validate(sessionId);
        return new ResponseEntity<>(sessionManager.getStructureCache(UUID.fromString(sessionId))
                .getModels()
                .stream()
                .map(PdbModel::getModelNumber)
                .collect(Collectors.toSet()), HttpStatus.OK);
    }

    private Optional<PdbModel> extractModel(PdbStructure structure, Integer model) {
        return structure.getModels()
                .stream()
                .filter(pdbModel -> Integer.valueOf(pdbModel.getModelNumber()).equals(model))
                .findFirst();
    }

    private Optional<PdbChain> extractChain(PdbModel model, String chain) {
        return model.getChains()
                .stream()
                .filter(pdbChain -> pdbChain.toString().equals(chain))
                .findFirst();
    }


}
