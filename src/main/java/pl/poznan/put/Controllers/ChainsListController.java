package pl.poznan.put.Controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.poznan.put.Session.SessionManager;
import pl.poznan.put.Structure.PdbStructureChains;

import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class ChainsListController {

    private final SessionManager sessionManager;

    @RequestMapping("/chains")
    public final List<String> chains(@PathVariable("sessionId") final String sessionId) {
        if (this.sessionManager.hasSession(UUID.fromString(sessionId))) {
            return new PdbStructureChains(this.sessionManager.getSession(UUID.fromString(sessionId)).getStructure()).getList();
        } else {
            throw new NotFoundException("");
        }
    }
}
