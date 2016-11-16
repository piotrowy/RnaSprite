package pl.poznan.put.Controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.poznan.put.RnaCommons.ResidueInfo;
import pl.poznan.put.RnaMatrix.RnaMatrix;
import pl.poznan.put.TorsionAnglesMatrix.AngleData;
import pl.poznan.put.TorsionAnglesMatrix.TorsionAnglesMatrixProvider;

import javax.inject.Inject;
import java.util.List;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class TorsionAnglesMatrixController {

    private final TorsionAnglesMatrixProvider torsionAnglesMatrixProvider;

    @RequestMapping("/torsionAnglesMatrix")
    public final HttpEntity<List<RnaMatrix<String, ResidueInfo, AngleData>>> torsionAnglesMatrix(@RequestParam(value = "sessionId") final String sessionId) {
        return null;
    }
}
