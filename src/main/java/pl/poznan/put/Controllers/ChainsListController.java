package pl.poznan.put.Controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.poznan.put.RnaCommons.PdbStructureChains;
import pl.poznan.put.Session.SessionManager;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class ChainsListController {

    private final SessionManager sessionManager;

    @RequestMapping("/chains")
    public final List<String> chains(@RequestParam(value = "sessionId") final String sessionId) {
        return new PdbStructureChains(this.sessionManager.getSession(UUID.fromString(sessionId)).getStructure()).getList();
    }
}
