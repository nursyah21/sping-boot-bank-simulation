package com.nurs.backend.model;


import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@SQLDelete(sql = "UPDATE employees SET is_deleted = true WHERE id=?")
@SQLRestriction("is_deleted = false")
@Table(name = "employees")
public class Employee extends BaseModel{
    private String firstName;
    
    private String lastName;

    @Column(unique = true)
    private String emailId;
}
