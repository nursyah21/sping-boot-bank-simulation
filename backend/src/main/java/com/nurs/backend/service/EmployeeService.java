package com.nurs.backend.service;

import java.util.List;

import com.nurs.backend.dto.EmployeeRequest;
import com.nurs.backend.model.Employee;

public interface EmployeeService {
    List<Employee> getAllEmployees();
    Employee saveEmployee(EmployeeRequest employee);
    Employee getEmployeeById(Long id);
    Employee updateEmployee(Long id, EmployeeRequest employee);
    void deleteEmployee(Long id);
}
