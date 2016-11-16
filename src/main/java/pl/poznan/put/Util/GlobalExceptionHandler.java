package pl.poznan.put.Util;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.poznan.put.Exceptions.InvalidMailAddressException;
import pl.poznan.put.Exceptions.PdbDoesNotExistException;
import pl.poznan.put.Exceptions.StructureIsEmptyException;
import pl.poznan.put.Exceptions.StructureNotFoundException;

import javax.inject.Inject;

@ControllerAdvice
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class GlobalExceptionHandler {

    private final ConfigService configService;

    @ExceptionHandler(StructureNotFoundException.class)
    public HttpEntity<String> structureNotFoundExceptionHandler() {
        return new ResponseEntity<>(this.configService.getExceptionNotFoundMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidMailAddressException.class)
    public HttpEntity<String> invalidMailAddressExceptionHandler() {
        return new ResponseEntity<>(this.configService.getExceptionInvalidEmailAddressMessage(), HttpStatus.PRECONDITION_FAILED);
    }

    @ExceptionHandler(PdbDoesNotExistException.class)
    public HttpEntity<String> PdbDoesNotExistException() {
        return new ResponseEntity<>(this.configService.getExceptionNotFoundMessage(), HttpStatus.PRECONDITION_FAILED);
    }

    @ExceptionHandler(StructureIsEmptyException.class)
    public HttpEntity<String> StructureIsEmptyException() {
        return new ResponseEntity<>(this.configService.getExceptionStructureIsEmptyMessage(), HttpStatus.PRECONDITION_FAILED);
    }

}
