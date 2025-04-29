package org.example.service;

import org.example.dto.Employee;

public interface EmployeeService {
    boolean addEmployee(Employee employee);

    boolean emailExists(String email);

    Employee updateEmployee(Employee employee);
}
