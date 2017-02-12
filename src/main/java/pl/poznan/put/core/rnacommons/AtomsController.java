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
public class AtomsController {

    private final AtomNamesList atomNamesList;

    @RequestMapping("/atoms")
    public final HttpEntity<List<String>> atoms() {
        return new ResponseEntity<>(atomNamesList.getList(), HttpStatus.OK);
    }
}