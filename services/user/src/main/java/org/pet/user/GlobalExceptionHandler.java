package org.pet.user;

import org.pet.user.exception.IllegalAmountException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalAmountException.class)
    public ResponseEntity<?> handleIllegalAmountException(IllegalAmountException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
