package com.digitinary.customerservice.exception;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(Long id) {
        super("Customer not found for this id :: " + id);
    }
}
