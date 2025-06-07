package com.prueba.tecnica.nttdata.gestion_employee.exception;
import java.util.HashMap;
import java.util.Map;

import javax.security.auth.login.CredentialException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.persistence.EntityNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<Map<String,String>> validationException(MethodArgumentNotValidException ex){
        Map<String,String> resultado = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> resultado.put(error.getField(), error.getDefaultMessage()));
        return new ResponseEntity<Map<String,String>>(resultado,HttpStatus.BAD_REQUEST);
    } 
    
    @ExceptionHandler(EntityNotFoundException.class)
    ResponseEntity<Map<String,String>> entityNotFoundException(EntityNotFoundException ex){
        return new ResponseEntity<Map<String,String>>(Map.of("message",ex.getMessage()),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<Map<String,String>> illegalArgumentException(IllegalArgumentException ex){
        return new ResponseEntity<Map<String,String>>(Map.of("message",ex.getMessage()),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    ResponseEntity<Map<String,String>> dataIntegrationViolationException(DataIntegrityViolationException ex){
        return new ResponseEntity<Map<String,String>>(Map.of("message",ex.getMessage()),HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    ResponseEntity<Map<String,String>> usernameNotFoundException(UsernameNotFoundException ex){
        return new ResponseEntity<Map<String,String>>(Map.of("message",ex.getMessage()),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CredentialException.class)
    ResponseEntity<Map<String,String>> credentialException(CredentialException ex){
        return new ResponseEntity<Map<String,String>>(Map.of("message",ex.getMessage()),HttpStatus.NOT_FOUND);
    }



}
