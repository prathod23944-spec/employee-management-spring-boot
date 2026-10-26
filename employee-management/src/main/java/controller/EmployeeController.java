package com.example.employee_management.controller;

import com.example.employee_management.entity.Employee;
import com.example.employee_management.service.EmployeeService;

import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "*")
public class EmployeeController {

    private static final Logger logger =
            LoggerFactory.getLogger(EmployeeController.class);

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // CREATE - Add employee
    @PostMapping
    public ResponseEntity<Employee> addEmployee(
            @Valid @RequestBody Employee employee) {

        Employee savedEmployee =
                employeeService.addEmployee(employee);

        logger.info("Employee created successfully with ID: {}",
                savedEmployee.getId());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedEmployee);
    }

    // READ - Get all employees
    @GetMapping
    public List<Employee> getAllEmployees() {

        List<Employee> employees =
                employeeService.getAllEmployees();

        logger.info("Fetched all employees. Total count: {}",
                employees.size());

        return employees;
    }

    // READ - Get employee by ID
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(
            @PathVariable Long id) {

        Employee employee =
                employeeService.getEmployeeById(id);

        if (employee == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Employee not found with ID: " + id
            );
        }

        logger.info("Employee fetched successfully with ID: {}", id);

        return ResponseEntity.ok(employee);
    }

    // UPDATE - Update employee
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody Employee employeeDetails) {

        Employee updatedEmployee =
                employeeService.updateEmployee(id, employeeDetails);

        if (updatedEmployee == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Employee not found with ID: " + id
            );
        }

        logger.info("Employee updated successfully with ID: {}", id);

        return ResponseEntity.ok(updatedEmployee);
    }

    // DELETE - Delete employee
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(
            @PathVariable Long id) {

        Employee employee =
                employeeService.getEmployeeById(id);

        if (employee == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Employee not found with ID: " + id
            );
        }

        employeeService.deleteEmployee(id);

        logger.info("Employee deleted successfully with ID: {}", id);

        return ResponseEntity.noContent().build();
    }

   /* // TEMPORARY TEST - Generic Exception Handler
    // Remove this method before production deployment
    @GetMapping("/test-error")
    public ResponseEntity<Void> testGenericException() {
        throw new RuntimeException("Test exception"); */
    }

