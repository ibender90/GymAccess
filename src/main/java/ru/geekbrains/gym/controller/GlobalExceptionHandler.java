package ru.geekbrains.gym.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.geekbrains.gym.dto.ExceptionDto;
import ru.geekbrains.gym.exceptions.AppException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AppException.class)
    @ResponseBody
    public ResponseEntity<ExceptionDto> handleAppExceptions(AppException ex) {

        return wrapIntoResponseEntity(
                new AppException(ex.getMessage() , HttpStatus.BAD_REQUEST.value()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseBody
    public ResponseEntity<ExceptionDto> handleBadCredentialsException(BadCredentialsException ex){

        return wrapIntoResponseEntity(
                new AppException("Provided email or password are incorrect", HttpStatus.BAD_REQUEST.value()),
                HttpStatus.BAD_REQUEST
        );
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<ExceptionDto> handleValidationExceptions(MethodArgumentNotValidException ex) {

       return wrapIntoResponseEntity(
               new AppException(collectFieldValidationErrors(ex).toString() , HttpStatus.BAD_REQUEST.value()),
               HttpStatus.BAD_REQUEST);
    }

    private Map<String, String> collectFieldValidationErrors(MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    private ResponseEntity<ExceptionDto> wrapIntoResponseEntity(AppException ex, HttpStatus status) {
        return ResponseEntity
                .status(status)
                .body(new ExceptionDto(ex));
    }
}
