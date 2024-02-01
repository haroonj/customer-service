package com.digitinary.customerservice.service;

import com.digitinary.customerservice.entity.Customer;
import com.digitinary.customerservice.exception.CustomerIdExistsException;
import com.digitinary.customerservice.exception.CustomerNotFoundException;
import com.digitinary.customerservice.exception.InvalidCustomerIdException;
import com.digitinary.customerservice.exception.InvalidCustomerTypeException;
import com.digitinary.customerservice.model.CustomerType;
import com.digitinary.customerservice.model.dto.CustomerDTO;
import com.digitinary.customerservice.model.mapper.CustomerMapper;
import com.digitinary.customerservice.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    public CustomerDTO createCustomer(CustomerDTO customer) {
        validateCustomerID(customer.getId());
        isValidCustomerType(customer.getType());
        if (customerRepository.existsById(customer.getId())) {
            throw new CustomerIdExistsException("Customer ID already exists.");
        }
        return CustomerMapper.toDTO(
                customerRepository.save(
                        CustomerMapper.toEntity(customer)
                ));
    }

    public CustomerDTO getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
        return CustomerMapper.toDTO(customer);
    }

    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream()
                .map(CustomerMapper::toDTO)
                .toList();
    }

    public CustomerDTO updateCustomer(Long id, CustomerDTO customerDetails) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
        isValidCustomerType(customerDetails.getType());
        customer.setName(customerDetails.getName());
        customer.setLegalId(customerDetails.getLegalId());
        customer.setType(CustomerType.valueOf(customerDetails.getType().toUpperCase()));
        customer.setAddress(customerDetails.getAddress());

        return CustomerMapper.toDTO(customerRepository.save(customer));
    }

    public void deleteCustomer(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
        customerRepository.delete(customer);
    }

    private void validateCustomerID(Long id) {
        String idString = Long.toString(id);
        if (!idString.matches("\\d{7}")) {
            throw new InvalidCustomerIdException("Customer ID must be exactly 7 digits.");
        }
    }

    public void isValidCustomerType(String enumName) {
        try {
            Enum.valueOf(CustomerType.class, enumName);
        } catch (IllegalArgumentException e) {
            throw new InvalidCustomerTypeException("Invalid Customer Type");
        }
    }
}
