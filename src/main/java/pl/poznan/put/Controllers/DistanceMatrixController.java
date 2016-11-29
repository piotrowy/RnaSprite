package pl.poznan.put.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.poznan.put.rnamatrix.Matrix;

@RestController
@RequestMapping("/matrix")
public class DistanceMatrixController {

    @RequestMapping("/distances")
    public final Matrix distanceMatrix(@RequestParam(value = "sessionId") final String sessionId) {
        return null;
    }

}
