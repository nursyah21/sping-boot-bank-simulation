package com.nurs.backend.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.nurs.backend.dto.EmployeeRequest;
import com.nurs.backend.model.Employee;

public interface EmployeeService {
    Page<Employee> getAllEmployees(Pageable pageable, String keyword);
    Employee saveEmployee(EmployeeRequest employee);
    Employee getEmployeeById(Long id);
    Employee updateEmployee(Long id, EmployeeRequest employee);
    void deleteEmployee(Long id);
}
