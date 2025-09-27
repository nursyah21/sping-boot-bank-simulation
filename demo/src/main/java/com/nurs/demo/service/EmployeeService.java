package com.nurs.demo.service;

import java.util.List;

import com.nurs.demo.model.Employee;

public interface EmployeeService {
    List<Employee> getAllEmployees();
    Employee savEmployee(Employee employee);
    Employee getEmployeeById(Long id);
    Employee updateEmployee(Long id, Employee employee);
    void deleteEmployee(Long id);
}
