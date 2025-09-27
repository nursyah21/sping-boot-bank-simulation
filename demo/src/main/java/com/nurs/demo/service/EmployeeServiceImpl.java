package com.nurs.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nurs.demo.exception.ResourceNotFoundException;
import com.nurs.demo.model.Employee;
import com.nurs.demo.repository.EmployeeRepository;

import lombok.val;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void deleteEmployee(Long id) {
        val employee = getEmployeeById(id);
        employeeRepository.delete(employee);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
            .orElseThrow(()-> new ResourceNotFoundException("Employee id not found"));
    }

    @Override
    public Employee savEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Employee updateEmployee(Long id, Employee employee) {
        val _employee = getEmployeeById(id);
        _employee.setFirstName(employee.getFirstName());
        _employee.setLastName(employee.getLastName());
        _employee.setEmailId(employee.getEmailId());

        return employeeRepository.save(_employee);
    }
    
}
