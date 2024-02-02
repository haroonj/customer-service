package com.digitinary.customerservice.service;

import com.digitinary.customerservice.entity.Customer;
import com.digitinary.customerservice.event.publisher.CustomerEventPublisher;
import com.digitinary.customerservice.exception.CustomerIdExistsException;
import com.digitinary.customerservice.exception.CustomerNotFoundException;
import com.digitinary.customerservice.exception.InvalidCustomerIdException;
import com.digitinary.customerservice.exception.InvalidCustomerTypeException;
import com.digitinary.customerservice.model.CustomerType;
import com.digitinary.customerservice.model.dto.CustomerDTO;
import com.digitinary.customerservice.model.mapper.CustomerMapper;
import com.digitinary.customerservice.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Slf4j
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerEventPublisher customerEventPublisher;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, CustomerEventPublisher customerEventPublisher) {
        this.customerRepository = customerRepository;
        this.customerEventPublisher = customerEventPublisher;
    }

    @Transactional
    public CustomerDTO createCustomer(CustomerDTO customer) {
        validateCustomerID(customer.getId());
        isValidCustomerType(customer.getType());
        if (customerRepository.existsById(customer.getId())) {
            throw new CustomerIdExistsException("Customer ID already exists.");
        }
        CustomerDTO customerDTO = CustomerMapper.toDTO(
                customerRepository.save(
                        CustomerMapper.toEntity(customer)
                ));
        log.debug("Customer created with values {}", customerDTO);
        customerEventPublisher.publishCustomerCreated(CustomerMapper.toEvent(customerDTO));
        return customerDTO;
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

        log.debug("Customer updated with values {}", customer);
        return CustomerMapper.toDTO(customerRepository.save(customer));
    }

    public void deleteCustomer(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
        customerRepository.delete(customer);
        customerEventPublisher.publishCustomerDeleted(CustomerMapper.toEvent(customer));

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
