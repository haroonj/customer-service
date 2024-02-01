package com.digitinary.customerservice.controller;

import com.digitinary.customerservice.exception.CustomerNotFoundException;
import com.digitinary.customerservice.model.dto.CustomerDTO;
import com.digitinary.customerservice.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
        CustomerDTO customer = new CustomerDTO();
        customer.setId(1234567L);
        customer.setName("أحمد محمد");
        customer.setLegalId("123456789");
        customer.setType("Individual");
        customer.setAddress("123 Main St");

        when(customerService.createCustomer(any(CustomerDTO.class))).thenReturn(customer);

        mockMvc.perform(post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("أحمد محمد"));
    }

    @Test
    void whenGetExistingCustomerById_thenStatusOkAndCustomerReturned() throws Exception {
        CustomerDTO customer = new CustomerDTO();
        customer.setId(1234567L);
        customer.setName("فاطمة علي");
        customer.setLegalId("987654321");
        customer.setType("retail");
        customer.setAddress("456 Elm St");

        when(customerService.getCustomerById(1234567L)).thenReturn(customer);

        mockMvc.perform(get("/customers/1234567"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("فاطمة علي"));
    }

    @Test
    void whenGetNonExistingCustomerById_thenStatusNotFound() throws Exception {
        Long customerId = 1234567L;
        given(customerService.getCustomerById(customerId)).willThrow(new CustomerNotFoundException(customerId));

        mockMvc.perform(get("/customers/1234567"))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenGetAllCustomers_thenStatusOkAndCustomersReturned() throws Exception {
        CustomerDTO customer1 = new CustomerDTO();
        customer1.setId(1234567L);
        customer1.setName("ياسمين خالد");

        CustomerDTO customer2 = new CustomerDTO();
        customer2.setId(2345678L);
        customer2.setName("عمر فاروق");

        when(customerService.getAllCustomers()).thenReturn(Arrays.asList(customer1, customer2));

        mockMvc.perform(get("/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("ياسمين خالد"))
                .andExpect(jsonPath("$[1].name").value("عمر فاروق"));
    }

    @Test
    void whenUpdateExistingCustomer_thenStatusOkAndCustomerUpdated() throws Exception {
        CustomerDTO customer = new CustomerDTO();
        customer.setId(1234567L);
        customer.setName("نور عادل");
        customer.setLegalId("123456789");
        customer.setType("Corporate");
        customer.setAddress("789 Pine St");

        when(customerService.updateCustomer(eq(1L), any(CustomerDTO.class))).thenReturn(customer);

        mockMvc.perform(put("/customers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("نور عادل"));
    }

    @Test
    void whenDeleteExistingCustomer_thenStatusOk() throws Exception {
        doNothing().when(customerService).deleteCustomer(1234567L);

        mockMvc.perform(delete("/customers/1234567"))
                .andExpect(status().isOk());
    }
}
