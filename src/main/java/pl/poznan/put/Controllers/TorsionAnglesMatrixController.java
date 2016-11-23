package pl.poznan.put.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.poznan.put.exceptions.MalformedSessionIdException;
import pl.poznan.put.rnamatrix.Matrix;
import pl.poznan.put.session.SessionData;
import pl.poznan.put.session.SessionManager;
import pl.poznan.put.torsionanglesmatrix.AngleData;
import pl.poznan.put.torsionanglesmatrix.ResidueInfo;
import pl.poznan.put.torsionanglesmatrix.TorsionAnglesMatrixProvider;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/matrix")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class TorsionAnglesMatrixController {

    private final TorsionAnglesMatrixProvider torsionAnglesMatrixProvider;
    private final SessionManager sessionManager;

    @RequestMapping("/torsion-angles/{sessionId}")
    public final HttpEntity<List<Matrix<ResidueInfo, String, AngleData>>> torsionAnglesMatrix(@PathVariable("sessionId") final String sessionId) {
        if (!this.sessionManager.hasSession(UUID.fromString(sessionId))) {
            throw new MalformedSessionIdException(sessionId);
        }
        SessionData sessionData = this.sessionManager.getSession(UUID.fromString(sessionId));
        if (sessionData.getTorsionAngles() == null) {
            sessionData.setTorsionAngles(this.torsionAnglesMatrixProvider.get(sessionData.getStructure()));
        }
        return new ResponseEntity<>(sessionData.getTorsionAngles(), HttpStatus.OK);
    }

    @RequestMapping("/torsion-angles/{sessionId}/{angles}")
    public final HttpEntity<List<Matrix<ResidueInfo, String, AngleData>>> torsionAnglesMatrix(@PathVariable("sessionId") final String sessionId,
                                                                                              @PathVariable("angles") final String angles) {
        return null;
    }

//    private final HttpEntity<List<Matrix<ResidueInfo, String, AngleData>>> validateAndGetetMatrix() {
//        if (!this.sessionManager.hasSession(UUID.fromString(sessionId))) {
//            throw new MalformedSessionIdException(sessionId);
//        }
//        SessionData sessionData = this.sessionManager.getSession(UUID.fromString(sessionId));
//        if (sessionData.getTorsionAngles() == null) {
//            sessionData.setTorsionAngles(this.torsionAnglesMatrixProvider.get(sessionData.getStructure()));
//        }
//        return new ResponseEntity<>(sessionData.getTorsionAngles(), HttpStatus.OK);
//    }
}
