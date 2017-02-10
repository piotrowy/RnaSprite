package pl.poznan.put.exceptions;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class UriErrorController implements ErrorController {

    private static final String ERROR_MESSAGE = "error.message";
    private static final String PATH = "/error";
    private final Environment env;

    @RequestMapping(PATH)
    public final ResponseEntity<List<String>> error() {
        return new ResponseEntity<>(Arrays.asList(env.getProperty(ERROR_MESSAGE).split("#")), HttpStatus.NOT_FOUND);
    }

    @Override
    public final String getErrorPath() {
        return PATH;
    }
}
