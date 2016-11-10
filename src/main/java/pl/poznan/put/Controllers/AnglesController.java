package pl.poznan.put.Controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.poznan.put.RnaCommons.GreekAngleName;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class AnglesController {

    private final GreekAngleName greekAngleName;

    @RequestMapping("/angles")
    public final List<String> angles() {
        return greekAngleName.getGreekAngleNamesList();
    }
}
