package pl.poznan.put.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.poznan.put.rnamatrix.csv.CsvMatrix;
import pl.poznan.put.session.SessionManager;
import pl.poznan.put.torsionanglesmatrix.TorsionAnglesMatrixProvider;

import javax.inject.Inject;
import java.io.File;

@RestController
@RequestMapping("/matrix")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class CsvController {

    private final TorsionAnglesMatrixProvider torsionAnglesMatrixProvider;
    private final SessionManager sessionManager;
    private final CsvMatrix csvMatrix;

    @RequestMapping("/csv/{sessionId}")
    public final HttpEntity<File> torsionAnglesMatrix(@PathVariable("sessionId") final String sessionId) {
        return null;
    }
}
