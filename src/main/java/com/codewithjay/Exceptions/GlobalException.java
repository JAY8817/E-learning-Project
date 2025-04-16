package com.codewithjay.Exceptions;


import com.codewithjay.Dto.CustomMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CustomMessage> handleResourceNotFound(ResourceNotFoundException e) {

        CustomMessage customMessage = new CustomMessage();
        customMessage.setMessage(e.getMessage());
        customMessage.setSuccess(false);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(customMessage);

    }

//    @ExceptionHandler(AuthorizationDeniedException.class)
//    public ResponseEntity<CustomMessage> handleAuthDeniedException(AuthorizationDeniedException e1) {
//
//        CustomMessage customMessage = new CustomMessage();
//        customMessage.setMessage(e1.getMessage());
//        customMessage.setSuccess(false);
//
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(customMessage);
//
//    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

       Map<String,String> errors = new HashMap<>();
       e.getBindingResult().getAllErrors().forEach((error) -> {
           String fieldName = ((FieldError) error).getField();
           String errorMessage = error.getDefaultMessage();
           errors.put(fieldName, errorMessage);
       }
       );

       return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);


    }
}
