package pl.poznan.put.Error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorResponse> exceptionHandler(final Exception ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Please contact your administrator");
        return new ResponseEntity<ErrorResponse>(error, HttpStatus.OK);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class ErrorResponse {
        private int errorCode;
        private String message;
    }
}
