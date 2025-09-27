package com.nurs.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nurs.demo.exception.CustomException;
import com.nurs.demo.model.Employee;
import com.nurs.demo.repository.EmployeeRepository;

import lombok.RequiredArgsConstructor;
import lombok.val;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

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
            .orElseThrow(()-> new CustomException("Employee id not found"));
    }

    @Override
    public Employee savEmployee(Employee employee) {
        employeeRepository.findByEmailId(employee.getEmailId()).ifPresent(u->{
            throw new CustomException("emailId already registered");
        });

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
