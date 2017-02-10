package pl.poznan.put.exceptions;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.poznan.put.core.mail.InvalidMailAddressException;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final String EXCEPTION_INVALID_ARGUMENT = "Your input data was wrong. Check if all your parameter does exist in chosen structure.";
    private static final String EXCEPTION_INVALID_EMAIL_ADDRESS = "Email address was not valid.";


    @ExceptionHandler(InvalidArgumentException.class)
    public final HttpEntity<String> structureNotFoundExceptionHandler() {
        return new ResponseEntity<>(EXCEPTION_INVALID_ARGUMENT, HttpStatus.PRECONDITION_FAILED);
    }

    @ExceptionHandler(InvalidMailAddressException.class)
    public final HttpEntity<String> invalidMailAddressExceptionHandler() {
        return new ResponseEntity<>(EXCEPTION_INVALID_EMAIL_ADDRESS, HttpStatus.PRECONDITION_FAILED);
    }
}
