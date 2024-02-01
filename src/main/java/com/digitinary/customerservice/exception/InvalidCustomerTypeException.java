package com.digitinary.customerservice.exception;

public class InvalidCustomerTypeException extends RuntimeException {
    public InvalidCustomerTypeException(String message) {
        super(message);
    }
}
