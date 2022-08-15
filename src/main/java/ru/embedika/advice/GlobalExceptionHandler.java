package ru.embedika.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.embedika.exception.*;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApplicationError> catchResourceNotFoundException(ResourceNotFoundException e) {
        return new ResponseEntity<>(
                new ApplicationError(HttpStatus.NOT_FOUND.value(), e.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(NoContentException.class)
    public ResponseEntity<ApplicationError> catchNoContentException(NoContentException e) {
        return new ResponseEntity<>(
                new ApplicationError(HttpStatus.NOT_FOUND.value(), e.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(NoObjectCreatedException.class)
    public ResponseEntity<ApplicationError> catchNoObjectCreated(NoObjectCreatedException e) {
        return new ResponseEntity<>(
                new ApplicationError(HttpStatus.BAD_REQUEST.value(), e.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(NoObjectDeletedException.class)
    public ResponseEntity<ApplicationError> catchNoObjectCreated(NoObjectDeletedException e) {
        return new ResponseEntity<>(
                new ApplicationError(HttpStatus.BAD_REQUEST.value(), e.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }
}
