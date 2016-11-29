package pl.poznan.put.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.poznan.put.rnacommons.GreekAnglesNames;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class AnglesController {

    private final GreekAnglesNames greekAnglesNames;

    @RequestMapping("/angles")
    public final HttpEntity<List<String>> angles() {
        return new ResponseEntity<>(greekAnglesNames.getGreekAngleNamesList(), HttpStatus.OK);
    }
}
