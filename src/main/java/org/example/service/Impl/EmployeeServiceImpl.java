package org.example.service.Impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.dto.Employee;
import org.example.entity.EmployeeEntity;
import org.example.repository.EmployeeRepository;
import org.example.service.EmployeeService;
import org.example.util.Department;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper mapper;

    @Override
    public boolean addEmployee(Employee employee) {


        if (emailExists(employee.getEmail())){
            return false;
        }else {
            EmployeeEntity save = employeeRepository.save(mapper.map(employee, EmployeeEntity.class));

            if (save != null){
                return true;
            }
            return false;
        }
    }

    @Override
    public boolean emailExists(String email) {
        return employeeRepository.existsByEmail(email);
    }

    @Override
    public Employee updateEmployee(Employee employee) {

        EmployeeEntity byId = employeeRepository.findById(employee.getId())
                .orElseThrow(() -> new EntityNotFoundException("Employee with ID " + employee.getId() + " does not exist"));;

        if (!byId.getEmail().equals(employee.getEmail()) &&
                employeeRepository.existsByEmail(employee.getEmail())) {
            throw new IllegalArgumentException("Email already in use by another employee");
        }

        byId.setName(employee.getName());
        byId.setEmail(employee.getEmail());
        byId.setDepartment(Department.valueOf(employee.getDepartment()));

        return mapper.map(employeeRepository
                .save(mapper.map(employee , EmployeeEntity.class))
                ,Employee.class);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll()
                .stream().map(employeeEntity -> mapper.map(employeeEntity , Employee.class))
                .toList();
    }

    @Override
    public Boolean deleteEmployeee(Integer id) {
        if (employeeRepository.existsById(id)){
            employeeRepository.deleteById(id);
            return true;
        }else {
            return false;
        }
    }
}
