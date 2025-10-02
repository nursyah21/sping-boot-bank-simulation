package com.nurs.backend.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.nurs.backend.dto.EmployeeRequest;
import com.nurs.backend.dto.GenericResponse;
import com.nurs.backend.model.Employee;
import com.nurs.backend.service.EmployeeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping
    public GenericResponse<Page<Employee>> getAllEmployees(
        @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC)
        Pageable pageable,
        @RequestParam(required = false) String keyword
    ) {
        return new GenericResponse<>(
            "get employee success", 
            employeeService.getAllEmployees(pageable, keyword)
        );
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GenericResponse<Employee> createEmployee(
        @Valid @RequestBody EmployeeRequest employee
    ) {
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
    public GenericResponse<Employee> updateEmployee(
        @PathVariable Long id, @Valid @RequestBody EmployeeRequest employee
    ) {
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
