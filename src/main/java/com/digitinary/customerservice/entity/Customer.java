package com.digitinary.customerservice.entity;

import com.digitinary.customerservice.model.CustomerType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    private Long id;
    private String name;
    private String legalId;
    @Enumerated(EnumType.STRING)
    private CustomerType type;
    private String address;

}
