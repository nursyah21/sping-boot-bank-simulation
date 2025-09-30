package com.nurs.backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.nurs.backend.dto.EmployeeRequest;
import com.nurs.backend.dto.GenericResponse;
import com.nurs.backend.model.Employee;
import com.nurs.backend.service.EmployeeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public GenericResponse<List<Employee>> getAllEmployees() {
        return new GenericResponse<>(
            "get employee success", 
            employeeService.getAllEmployees()
        );
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GenericResponse<Employee> createEmployee(@Valid @RequestBody EmployeeRequest employee) {
        return new GenericResponse<>(
            "create new employee success", 
            employeeService.saveEmployee(employee)
        );
    }

    @GetMapping("/{id}")
    public GenericResponse<Employee> getEmployeeById(@PathVariable Long id) {
        return new GenericResponse<>(
            "get employee by id success", 
            employeeService.getEmployeeById(id)
        );    
    }

    @PutMapping("/{id}")
    public GenericResponse<Employee> updateEmployee(@PathVariable Long id, @Valid @RequestBody EmployeeRequest employee) {
        return new GenericResponse<>(
            "update employee success",  
            employeeService.updateEmployee(id, employee)
        );
    }

    @DeleteMapping("/{id}")
    public GenericResponse<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return new GenericResponse<>("delete employee success", null);
    }
}
