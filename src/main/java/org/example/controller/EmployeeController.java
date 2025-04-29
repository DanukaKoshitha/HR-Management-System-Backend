package org.example.controller;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.dto.Employee;
import org.example.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/employee")
@RequiredArgsConstructor

public class EmployeeController {

    private final EmployeeService service;

    @PostMapping("/create")
    public ResponseEntity<String> addEmployee(@RequestBody Employee employee) {

        if (service.emailExists(employee.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
        }

        boolean isAdded = service.addEmployee(employee);
        if (isAdded) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Employee created successfully");
        } else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to add employee");
        }
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateEmployee(@RequestBody Employee employee) {
        try {
            Employee updated = service.updateEmployee(employee);
            return ResponseEntity.ok(updated);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update employee");
        }
    }

    @GetMapping("/view")
    public List<Employee> getAll(){
        return service.getAllEmployees();
    }
}
