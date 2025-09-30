package com.nurs.backend.repository;

import org.springframework.stereotype.Repository;

import com.nurs.backend.model.Employee;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmailId(String emailId);
    Optional<Employee> findByEmailIdAndIdNot(String emailId, Long id);
}
