package com.digitinary.customerservice.model.mapper;

import com.digitinary.customerservice.entity.Customer;
import com.digitinary.customerservice.model.CustomerType;
import com.digitinary.customerservice.model.dto.CustomerDTO;
import com.digitinary.customerservice.event.model.CustomerEvent;

public class CustomerMapper {
    private CustomerMapper() {
    }

    public static Customer toEntity(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setId(customerDTO.getId());
        customer.setName(customerDTO.getName());
        customer.setLegalId(customerDTO.getLegalId());
        customer.setType(CustomerType.valueOf(customerDTO.getType().toUpperCase()));
        customer.setAddress(customerDTO.getAddress());
        return customer;
    }

    public static CustomerDTO toDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setName(customer.getName());
        customerDTO.setLegalId(customer.getLegalId());
        customerDTO.setType(customer.getType().name());
        customerDTO.setAddress(customer.getAddress());
        return customerDTO;
    }

    public static CustomerEvent toEvent(CustomerDTO customerDTO) {
        CustomerEvent event = new CustomerEvent();
        event.setCustomerId(customerDTO.getId());
        event.setName(customerDTO.getName());
        event.setLegalId(customerDTO.getLegalId());
        event.setType(customerDTO.getType());
        event.setAddress(customerDTO.getAddress());
        return event;
    }
    public static CustomerEvent toEvent(Customer customer) {
        CustomerEvent event = new CustomerEvent();
        event.setCustomerId(customer.getId());
        event.setName(customer.getName());
        event.setLegalId(customer.getLegalId());
        event.setType(String.valueOf(customer.getType()));
        event.setAddress(customer.getAddress());
        return event;
    }
}
