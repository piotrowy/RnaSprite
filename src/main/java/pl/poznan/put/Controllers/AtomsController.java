package pl.poznan.put.Controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.poznan.put.RnaCommons.AtomNamesList;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class AtomsController {

    private final AtomNamesList atomNamesList;

    @RequestMapping("/atoms")
    public final List<String> atoms() {
        return atomNamesList.getList();
    }
}
