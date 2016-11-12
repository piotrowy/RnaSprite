package pl.poznan.put.Controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.poznan.put.Util.ConfigService;

import javax.inject.Inject;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Inject))

public class UriController implements ErrorController {

    private final ConfigService configService;

    private static final String PATH = "/error";

    @RequestMapping(PATH)
    public final ResponseEntity<String> error() {
        return new ResponseEntity<>(this.configService.getErrorMessage(), HttpStatus.OK);
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }
}
