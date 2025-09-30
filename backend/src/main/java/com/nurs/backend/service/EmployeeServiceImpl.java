package com.nurs.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nurs.backend.dto.EmployeeRequest;
import com.nurs.backend.exception.CustomException;
import com.nurs.backend.mapper.EmployeeMapper;
import com.nurs.backend.model.Employee;
import com.nurs.backend.repository.EmployeeRepository;

import lombok.RequiredArgsConstructor;
import lombok.val;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

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
    public Employee saveEmployee(EmployeeRequest employee) {
        employeeRepository.findByEmailId(employee.getEmailId()).ifPresent(u->{
            throw new CustomException("emailId already registered");
        });

        val _employee = employeeMapper.toEntity(employee);

        return employeeRepository.save(_employee);
    }

    @Override
    public Employee updateEmployee(Long id, EmployeeRequest employee) {
        val _employee = getEmployeeById(id);
        employeeRepository.findByEmailIdAndIdNot(employee.getEmailId(), id).ifPresent(u->{
            throw new CustomException("emailId already registered");
        });

        employeeMapper.updateEmployeeFromDto(employee, _employee);

        return employeeRepository.save(_employee);
    }
    
}
