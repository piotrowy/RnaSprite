package pl.poznan.put.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.poznan.put.exceptions.MalformedSessionIdException;
import pl.poznan.put.rna.torsion.RNATorsionAngleType;
import pl.poznan.put.rnamatrix.Matrix;
import pl.poznan.put.session.SessionManager;
import pl.poznan.put.torsionanglesmatrix.AngleData;
import pl.poznan.put.torsionanglesmatrix.ResidueInfo;
import pl.poznan.put.torsionanglesmatrix.TorsionAnglesMatrixFilter;
import pl.poznan.put.torsionanglesmatrix.TorsionAnglesMatrixProvider;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/matrix")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class TorsionAnglesMatrixController {

    private final TorsionAnglesMatrixProvider torsionAnglesMatrixProvider;
    private final SessionManager sessionManager;

    @SuppressWarnings("unchecked")
    @RequestMapping("/torsion-angles/{sessionId}")
    public final HttpEntity<List<Matrix<ResidueInfo, String, AngleData>>> torsionAnglesMatrix(@PathVariable("sessionId") final String sessionId) {
        return sessionManager.getSession(UUID.fromString(sessionId)).map(sessionData -> {
            if (!sessionData.getTorsionAngles().isPresent()) {
                sessionData.setTorsionAngles(torsionAnglesMatrixProvider.create(sessionData.getStructure(), Collections.EMPTY_SET));
            }
            return new ResponseEntity<>(sessionData.getTorsionAngles().get(), HttpStatus.OK);
        }).orElseThrow(() -> new MalformedSessionIdException(sessionId));
    }

    @SuppressWarnings("unchecked")
    @RequestMapping("/torsion-angles/{sessionId}/{angles}")
    public final HttpEntity<List<Matrix<ResidueInfo, String, AngleData>>> torsionAnglesMatrix(@PathVariable("sessionId") final String sessionId,
                                                                                              @PathVariable("angles") final String... angles) {
        return sessionManager.getSession(UUID.fromString(sessionId)).map(sessionData -> {
            if (sessionData.getTorsionAngles() == null) {
                sessionData.setTorsionAngles(torsionAnglesMatrixProvider.create(sessionData.getStructure(), Collections.EMPTY_SET));
            }
            return new ResponseEntity<>(sessionData.getTorsionAngles().get().stream()
                    .map(matrix -> TorsionAnglesMatrixFilter.builder().matrix(matrix).build().filter(anglesMapper(Arrays.asList(angles))))
                    .collect(Collectors.toList()), HttpStatus.OK);
        }).orElseThrow(() -> new MalformedSessionIdException(sessionId));
    }

    private Set<RNATorsionAngleType> anglesMapper(List<String> angles) {
        return angles.stream()
                .map(angle -> RNATorsionAngleType.valueOf(angle.toUpperCase()))
                .collect(Collectors.toSet());
    }
}
