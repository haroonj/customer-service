package com.digitinary.customerservice.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@Slf4j
@ControllerAdvice
public class CustomerExceptionHandler {

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<Object> handleCustomerNotFoundException(CustomerNotFoundException ex) {
        log.error("Customer Not Found Exception {}", ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CustomerIdExistsException.class)
    public ResponseEntity<Object> customerIdExistsExceptionHandler(CustomerIdExistsException ex) {
        log.error("customerIdExistsException {}", ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidCustomerIdException.class)
    public ResponseEntity<Object> invalidCustomerIdExceptionHandler(InvalidCustomerIdException ex) {
        log.error("InvalidCustomerIdException {}", ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(InvalidCustomerTypeException.class)
    public ResponseEntity<Object> invalidCustomerTypeExceptionHandler(InvalidCustomerTypeException ex) {
        log.error("InvalidCustomerTypeException {}", ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGlobalException(Exception ex) {
        log.error("Exception {}", ex.getMessage());
        return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
