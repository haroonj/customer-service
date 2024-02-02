package com.digitinary.customerservice.controller;

import com.digitinary.customerservice.model.dto.CustomerDTO;
import com.digitinary.customerservice.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerDTO customer) {
        log.debug("creating customer request with values {}", customer);
        return ResponseEntity.ok(customerService.createCustomer(customer));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id) {
        log.debug("get customer by ID request with id {}", id);
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    @GetMapping
    public List<CustomerDTO> getAllCustomers() {
        log.debug("get all customers");
        return customerService.getAllCustomers();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO customer) {
        log.debug("update customer with id {}", id);
        return ResponseEntity.ok(customerService.updateCustomer(id, customer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        log.debug("delete customer with id {}", id);
        customerService.deleteCustomer(id);
        return ResponseEntity.ok().build();
    }
}
