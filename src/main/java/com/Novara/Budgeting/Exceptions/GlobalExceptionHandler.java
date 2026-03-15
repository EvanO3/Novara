package com.Novara.Budgeting.Exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.Novara.Budgeting.DTOs.APIResponse;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    /*This gets activated when hibernate validates the entity */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<APIResponse> myConstraintViolationException(ConstraintViolationException e){
        Map<String, String> errors = new HashMap<>();

       e.getConstraintViolations().forEach(constraint ->{
        String fieldName = constraint.getPropertyPath().toString();
        String message = constraint.getMessage();
        errors.put(fieldName, message);
       });

       APIResponse response = new APIResponse("Validation Failed", false, errors);
       return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<APIResponse> myResourceNotFound(ResourceNotFound e){
        String message = e.getMessage();
        APIResponse response = new APIResponse(message, false);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

    }


      @ExceptionHandler(ApiException.class)
    public ResponseEntity<APIResponse> myApiException(ApiException e){
        String message = e.getMessage();
        APIResponse response = new APIResponse(message, false);
        return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);

    }


    // catch-all — anything you didn't anticipate
@ExceptionHandler(Exception.class)
public ResponseEntity<APIResponse> handleGeneric(Exception e) {
    String message = e.getMessage();
    return new ResponseEntity<>(
        new APIResponse(message, false),
        HttpStatus.INTERNAL_SERVER_ERROR  // 500
    );
}
    

@ExceptionHandler(IllegalArgumentException.class)
public ResponseEntity<APIResponse> handleBadRequest(IllegalArgumentException e) {
    return new ResponseEntity<>(
        new APIResponse(e.getMessage(), false),
        HttpStatus.BAD_REQUEST
    );
}

/*This gets activated when the request body is being validated 
in the controller */

@ExceptionHandler(MethodArgumentNotValidException.class)
public ResponseEntity<APIResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
    Map<String, String> errors = new HashMap<>();
    e.getBindingResult().getFieldErrors().forEach(error -> {
        errors.put(error.getField(), error.getDefaultMessage());
    });

    return new ResponseEntity<>(
        new APIResponse("Validation failed", false, errors),
        HttpStatus.BAD_REQUEST
    );
}



}


