package com.digitinary.customerservice.repository;

import com.digitinary.customerservice.entity.Customer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void testSaveCustomer() {

        Customer customer = new Customer();
        customer.setName("Haroun Jaradat");
        customer.setLegalId("123456789");
        customer.setType("Individual");
        customer.setAddress("123 Main St");


        Customer savedCustomer = customerRepository.save(customer);


        Assertions.assertNotNull(savedCustomer.getId());
        Assertions.assertEquals("Haroun Jaradat", savedCustomer.getName());
        Assertions.assertEquals("123456789", savedCustomer.getLegalId());
        Assertions.assertEquals("Individual", savedCustomer.getType());
        Assertions.assertEquals("123 Main St", savedCustomer.getAddress());
    }

    @Test
    void testFindCustomerById() {
        Customer customer = new Customer();
        customer.setName("Haroun Jaradat");
        customer.setLegalId("987654321");
        customer.setType("Business");
        customer.setAddress("456 Elm St");

        Customer savedCustomer = entityManager.persistAndFlush(customer);

        Customer foundCustomer = customerRepository.findById(savedCustomer.getId()).orElse(null);

        Assertions.assertNotNull(foundCustomer);
        Assertions.assertEquals(savedCustomer.getId(), foundCustomer.getId());
        Assertions.assertEquals("Haroun Jaradat", foundCustomer.getName());
        Assertions.assertEquals("987654321", foundCustomer.getLegalId());
        Assertions.assertEquals("Business", foundCustomer.getType());
        Assertions.assertEquals("456 Elm St", foundCustomer.getAddress());
    }

}