package com.digitinary.customerservice.model.mapper;

import com.digitinary.customerservice.entity.Customer;
import com.digitinary.customerservice.model.CustomerType;
import com.digitinary.customerservice.model.dto.CustomerDTO;

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
}
