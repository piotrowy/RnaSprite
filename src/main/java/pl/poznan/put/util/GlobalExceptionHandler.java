package pl.poznan.put.util;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.poznan.put.exceptions.InvalidMailAddressException;
import pl.poznan.put.exceptions.PdbDoesNotExistException;
import pl.poznan.put.exceptions.StructureIsEmptyException;
import pl.poznan.put.exceptions.StructureNotFoundException;

import javax.inject.Inject;

@ControllerAdvice
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class GlobalExceptionHandler {

    private final ConfigService configService;

    @ExceptionHandler(StructureNotFoundException.class)
    public final HttpEntity<String> structureNotFoundExceptionHandler() {
        return new ResponseEntity<>(this.configService.getExceptionNotFoundMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidMailAddressException.class)
    public final HttpEntity<String> invalidMailAddressExceptionHandler() {
        return new ResponseEntity<>(this.configService.getExceptionInvalidEmailAddressMessage(), HttpStatus.PRECONDITION_FAILED);
    }

    @ExceptionHandler(PdbDoesNotExistException.class)
    public final HttpEntity<String> pdbDoesNotExistException() {
        return new ResponseEntity<>(this.configService.getExceptionNotFoundMessage(), HttpStatus.PRECONDITION_FAILED);
    }

    @ExceptionHandler(StructureIsEmptyException.class)
    public final HttpEntity<String> structureIsEmptyException() {
        return new ResponseEntity<>(this.configService.getExceptionStructureIsEmptyMessage(), HttpStatus.PRECONDITION_FAILED);
    }

}
