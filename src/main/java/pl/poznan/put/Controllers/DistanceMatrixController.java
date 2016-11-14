package pl.poznan.put.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.poznan.put.RnaMatrix.RnaMatrix;

@RestController
@RequestMapping("/distanceMatrix")
public class DistanceMatrixController {

    @RequestMapping("/distanceMatrix")
    public final RnaMatrix distanceMatrix(@RequestParam(value = "sessionId") final String sessionId) {
        return null;
    }

}
