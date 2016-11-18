package pl.poznan.put.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.poznan.put.RnaMatrix.RnaMatrix;

@RestController
@RequestMapping("/matrix")
public class DistanceMatrixController {

    @RequestMapping("/distances")
    public final RnaMatrix distanceMatrix(@RequestParam(value = "sessionId") final String sessionId) {
        return null;
    }

}
