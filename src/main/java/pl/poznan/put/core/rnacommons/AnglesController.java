package pl.poznan.put.core.rnacommons;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import javax.inject.Inject;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class AnglesController {

    private final GreekAnglesNames greekAnglesNames;

    @RequestMapping("/angles")
    public final HttpEntity<List<String>> angles() {
        return new ResponseEntity<>(greekAnglesNames.getGreekAngleNamesList(), HttpStatus.OK);
    }
}