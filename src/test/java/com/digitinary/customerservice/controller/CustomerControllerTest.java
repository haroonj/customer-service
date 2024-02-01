package com.digitinary.customerservice.controller;

import com.digitinary.customerservice.entity.Customer;
import com.digitinary.customerservice.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void whenCreateCustomer_thenStatusOkAndCustomerCreated() throws Exception {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("أحمد محمد");
        customer.setLegalId("123456789");
        customer.setType("Individual");
        customer.setAddress("123 Main St");

        when(customerService.createCustomer(any(Customer.class))).thenReturn(customer);

        mockMvc.perform(post("/customers").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(customer))).andExpect(status().isOk()).andExpect(jsonPath("$.name").value("أحمد محمد"));
    }

    @Test
    void whenGetExistingCustomerById_thenStatusOkAndCustomerReturned() throws Exception {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("فاطمة علي");
        customer.setLegalId("987654321");
        customer.setType("Individual");
        customer.setAddress("456 Elm St");

        when(customerService.getCustomerById(1L)).thenReturn(Optional.of(customer));

        mockMvc.perform(get("/customers/1")).andExpect(status().isOk()).andExpect(jsonPath("$.name").value("فاطمة علي"));
    }

    @Test
    void whenGetNonExistingCustomerById_thenStatusNotFound() throws Exception {
        when(customerService.getCustomerById(2L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/customers/2")).andExpect(status().isNotFound());
    }

    @Test
    void whenGetAllCustomers_thenStatusOkAndCustomersReturned() throws Exception {
        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setName("ياسمين خالد");

        Customer customer2 = new Customer();
        customer2.setId(2L);
        customer2.setName("عمر فاروق");

        when(customerService.getAllCustomers()).thenReturn(Arrays.asList(customer1, customer2));

        mockMvc.perform(get("/customers")).andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(2)).andExpect(jsonPath("$[0].name").value("ياسمين خالد")).andExpect(jsonPath("$[1].name").value("عمر فاروق"));
    }

    @Test
    void whenUpdateExistingCustomer_thenStatusOkAndCustomerUpdated() throws Exception {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("نور عادل");
        customer.setLegalId("123456789");
        customer.setType("Corporate");
        customer.setAddress("789 Pine St");

        when(customerService.updateCustomer(eq(1L), any(Customer.class))).thenReturn(customer);

        mockMvc.perform(put("/customers/1").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(customer))).andExpect(status().isOk()).andExpect(jsonPath("$.name").value("نور عادل"));
    }

    @Test
    void whenDeleteExistingCustomer_thenStatusOk() throws Exception {
        doNothing().when(customerService).deleteCustomer(1L);

        mockMvc.perform(delete("/customers/1")).andExpect(status().isOk());
    }
}
