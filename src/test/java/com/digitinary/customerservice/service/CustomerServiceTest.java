package com.digitinary.customerservice.service;

import com.digitinary.customerservice.entity.Customer;
import com.digitinary.customerservice.exception.CustomerNotFoundException;
import com.digitinary.customerservice.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCustomer() {

        Customer customer = new Customer();
        customer.setName("Haroun Jaradat");

        when(customerRepository.save(customer)).thenReturn(customer);


        Customer createdCustomer = customerService.createCustomer(customer);


        assertNotNull(createdCustomer);
        assertEquals("Haroun Jaradat", createdCustomer.getName());
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    void testGetCustomerById() {

        Long customerId = 1L;
        Customer customer = new Customer();
        customer.setId(customerId);
        customer.setName("Haroun Jaradat");

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));


        Optional<Customer> foundCustomer = customerService.getCustomerById(customerId);


        assertTrue(foundCustomer.isPresent());
        assertEquals(customerId, foundCustomer.get().getId());
        assertEquals("Haroun Jaradat", foundCustomer.get().getName());
        verify(customerRepository, times(1)).findById(customerId);
    }

    @Test
    void testGetAllCustomers() {

        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer(1L, "Haroun Jaradat"));
        customers.add(new Customer(2L, "Mohammad Ahmad"));

        when(customerRepository.findAll()).thenReturn(customers);


        List<Customer> allCustomers = customerService.getAllCustomers();


        assertEquals(2, allCustomers.size());
        assertEquals("Haroun Jaradat", allCustomers.get(0).getName());
        assertEquals("Mohammad Ahmad", allCustomers.get(1).getName());
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    void testUpdateCustomer() {

        Long customerId = 1L;
        Customer existingCustomer = new Customer();
        existingCustomer.setId(customerId);
        existingCustomer.setName("Haroun Jaradat");

        Customer updatedCustomer = new Customer();
        updatedCustomer.setName("Mohammad Ahmad");

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(existingCustomer));
        when(customerRepository.save(existingCustomer)).thenReturn(existingCustomer);


        Customer result = customerService.updateCustomer(customerId, updatedCustomer);


        assertNotNull(result);
        assertEquals("Mohammad Ahmad", result.getName());
        verify(customerRepository, times(1)).findById(customerId);
        verify(customerRepository, times(1)).save(existingCustomer);
    }

    @Test
    void testDeleteCustomer() {

        Long customerId = 1L;
        Customer existingCustomer = new Customer();
        existingCustomer.setId(customerId);
        existingCustomer.setName("Haroun Jaradat");

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(existingCustomer));


        customerService.deleteCustomer(customerId);


        verify(customerRepository, times(1)).findById(customerId);
        verify(customerRepository, times(1)).delete(existingCustomer);
    }

    @Test
    void testDeleteCustomer_CustomerNotFound() {

        Long customerId = 1L;

        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> customerService.deleteCustomer(customerId));
        verify(customerRepository, times(1)).findById(customerId);
        verify(customerRepository, never()).delete(any());
    }
}