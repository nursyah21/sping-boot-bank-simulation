package com.nurs.demo.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank(message = "firstName is required")
    private String firstName;
    
    @NotBlank(message = "lastName is required")
    private String lastName;

    @NotBlank(message = "emailId is required")
    @Email(message = "email must be valid")
    @Column(unique = true)
    private String emailId;
}
