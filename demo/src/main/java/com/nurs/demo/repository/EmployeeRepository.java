package com.nurs.demo.repository;

import org.springframework.stereotype.Repository;

import com.nurs.demo.model.Employee;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmailId(String emailId);
}
