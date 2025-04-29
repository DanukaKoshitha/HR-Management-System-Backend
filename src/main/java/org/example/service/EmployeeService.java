package org.example.service;

import org.example.dto.Employee;

import java.util.List;

public interface EmployeeService {
    boolean addEmployee(Employee employee);

    boolean emailExists(String email);

    Employee updateEmployee(Employee employee);

    List<Employee> getAllEmployees();
}
