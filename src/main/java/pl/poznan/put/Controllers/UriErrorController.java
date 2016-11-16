package pl.poznan.put.Controllers;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.poznan.put.Util.ConfigService;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Inject))

public class UriErrorController implements ErrorController {

    private final ConfigService configService;

    private static final String PATH = "/error";

    @RequestMapping(PATH)
    public final ResponseEntity<List<String>> error() {
        return new ResponseEntity<>(Arrays.asList(this.configService.getErrorMessage().split("#")), HttpStatus.NOT_FOUND);
    }

    @Override
    public final String getErrorPath() {
        return PATH;
    }
}
