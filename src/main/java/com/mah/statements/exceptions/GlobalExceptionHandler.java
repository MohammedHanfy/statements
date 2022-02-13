package com.mah.statements.exceptions;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<GlobalError> handleMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException) {

        List<String> errors = methodArgumentNotValidException.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        GlobalError globalError = GlobalError.builder()
                .errorMessages(errors)
                .timestamp(LocalDate.now())
                .build();

        return new ResponseEntity(globalError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<GlobalError> handleEntityNotFoundException(EntityNotFoundException userNotFoundException) {

        GlobalError globalError = GlobalError.builder()
                .errorMessage(userNotFoundException.getMessage())
                .timestamp(LocalDate.now())
                .build();

        return new ResponseEntity(globalError, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }
}
