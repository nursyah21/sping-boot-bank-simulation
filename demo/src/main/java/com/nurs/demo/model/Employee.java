package com.nurs.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Employee {
    @Id
    @GeneratedValue
    private long id;

    private String firstName;
    private String lastName;
    private String emailId;
}
