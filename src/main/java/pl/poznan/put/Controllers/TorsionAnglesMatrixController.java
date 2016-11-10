package pl.poznan.put.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.poznan.put.RnaMatrix.RnaMatrix;

public class TorsionAnglesMatrixController {

    @RequestMapping("/torsionAnglesMatrix")
    public final RnaMatrix torsionAnglesMatrix(@RequestParam(value = "sessionId") final String sessionId) {
        return null;
    }
}
