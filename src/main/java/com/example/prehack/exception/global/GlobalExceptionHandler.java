package com.example.prehack.exception.global;

import com.example.prehack.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDate;

@RestControllerAdvice
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadLoginOrPasswordException.class)
    public ResponseEntity<ErrorDetails> badLoginOrPasswordException(Exception ex, WebRequest request) {
        log.error(ex.getMessage(), ex);
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.UNAUTHORIZED.value(), ConstantErrorCode.BUSINESS_ERROR_CODE_BAD_LOGIN_PASSWORD, LocalDate.now(), ex.getMessage(), request.getDescription(true));

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDetails);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> resourceNotFoundException(Exception ex, WebRequest request) {
        log.error(ex.getMessage(), ex);
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST.value(), ConstantErrorCode.BUSINESS_ERROR_CODE_VALIDATE, LocalDate.now(), ex.getMessage(), request.getDescription(false));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<ErrorDetails> userAlreadyExistException(Exception ex, WebRequest request) {
        log.error(ex.getMessage(), ex);
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.UNAUTHORIZED.value(), ConstantErrorCode.BUSINESS_ERROR_CODE_VALIDATE, LocalDate.now(), ex.getMessage(), request.getDescription(false));

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDetails);
    }

    @ExceptionHandler(TokenLifetimeExpiredException.class)
    public ResponseEntity<ErrorDetails> tokenLifetimeExpiredException(Exception ex, WebRequest request) {
        log.error(ex.getMessage(), ex);
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST.value(), ConstantErrorCode.BUSINESS_ERROR_CODE_LIFETIME_TOKEN, LocalDate.now(), ex.getMessage(), request.getDescription(false));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> globalExceptionHandler(Exception ex, WebRequest request) {
        log.error(ex.getMessage(), ex);
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.INTERNAL_SERVER_ERROR.value(), ConstantErrorCode.BUSINESS_ERROR_CODE_SERVER, LocalDate.now(), ex.getMessage(), request.getDescription(false));

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDetails);
    }
}
