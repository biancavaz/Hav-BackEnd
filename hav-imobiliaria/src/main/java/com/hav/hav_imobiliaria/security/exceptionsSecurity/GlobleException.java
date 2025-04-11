package com.hav.hav_imobiliaria.security.exceptionsSecurity;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.LocalDateTime;
import java.util.Objects;

@RestControllerAdvice
public class GlobleException {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorDetail> UserSecurityExceptionHandler(UserException e, WebRequest request) {

        ErrorDetail err =
                new ErrorDetail(e.getMessage(), request.getDescription(false), LocalDateTime.now());

        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MessageException.class)
    public ResponseEntity<ErrorDetail> MessageExceptionHandler(MessageException e, WebRequest request) {

        ErrorDetail err =
                new ErrorDetail(e.getMessage(), request.getDescription(false), LocalDateTime.now());

        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ChatException.class)
    public ResponseEntity<ErrorDetail> ChatExceptionHandler(ChatException e, WebRequest request) {

        ErrorDetail err =
                new ErrorDetail(e.getMessage(), request.getDescription(false), LocalDateTime.now());

        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetail> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e, WebRequest request) {

        String error = Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage();

        ErrorDetail err =
                new ErrorDetail(error, request.getDescription(false), LocalDateTime.now());

        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorDetail> MessageExceptionHandler(NoHandlerFoundException e, WebRequest request) {

        ErrorDetail err =
                new ErrorDetail("Endpoint not found", request.getDescription(false), LocalDateTime.now());

        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetail> otherExceptionHandler(Exception e, WebRequest request) {

        ErrorDetail err =
                new ErrorDetail(e.getMessage(), request.getDescription(false), LocalDateTime.now());

        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }
}
