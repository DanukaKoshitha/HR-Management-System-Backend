package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.Employee;
import org.example.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/employee")
@RequiredArgsConstructor

public class EmployeeController {

    private final EmployeeService service;

    @PostMapping("/create")
    public ResponseEntity<?> addEmployee(@RequestBody Employee employee){

         if (service.emailExists(employee.getEmail()) == true){
            return ResponseEntity.ok("Email already exists");
         }else {
             Boolean isAdded = service.addEmployee(employee);

             if (isAdded){
                 return ResponseEntity.ok(true);
             }
             return ResponseEntity.badRequest().body("Failed to add employee");
         }
    }
}
