package ru.embedika.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.embedika.dto.response.Response;
import ru.embedika.exception.BusinessException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<Response> catchResourceNotFoundException(BusinessException e) {
        return new ResponseEntity<>(new Response(
                HttpStatus.NOT_FOUND.value(), e.getMessage()), HttpStatus.NOT_FOUND
        );
    }
}
