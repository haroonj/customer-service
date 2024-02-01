package com.digitinary.customerservice.exception;

public class InvalidCustomerIdException extends RuntimeException {
    public InvalidCustomerIdException(String message) {
        super(message);
    }
}