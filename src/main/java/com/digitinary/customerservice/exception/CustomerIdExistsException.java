package com.digitinary.customerservice.exception;

public class CustomerIdExistsException extends RuntimeException {
    public CustomerIdExistsException(String message) {
        super(message);
    }
}