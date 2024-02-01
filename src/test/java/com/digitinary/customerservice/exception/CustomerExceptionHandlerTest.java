package com.digitinary.customerservice.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomerExceptionHandlerTest {

    @Test
    void handleCustomerNotFoundException_ShouldReturnNotFoundStatus() {
        CustomerExceptionHandler exceptionHandler = new CustomerExceptionHandler();
        CustomerNotFoundException exception = new CustomerNotFoundException("Customer not found");

        ResponseEntity<Object> response = exceptionHandler.handleCustomerNotFoundException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Customer not found", response.getBody());
    }

    @Test
    void handleGlobalException_ShouldReturnInternalServerErrorStatus() {
        CustomerExceptionHandler exceptionHandler = new CustomerExceptionHandler();
        Exception exception = new Exception("Internal server error");

        ResponseEntity<Object> response = exceptionHandler.handleGlobalException(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An error occurred: Internal server error", response.getBody());
    }
}