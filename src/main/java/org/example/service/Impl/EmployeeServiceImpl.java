package org.example.service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.dto.Employee;
import org.example.entity.EmployeeEntity;
import org.example.repository.EmployeeRepository;
import org.example.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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
}
