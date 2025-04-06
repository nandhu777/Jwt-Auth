package com.crud.example.cruds.service;
import java.util.List;
import java.util.Map;

import com.crud.example.cruds.model.Employee;
import org.springframework.stereotype.Service;


public interface EmployeeService {
    
    List<Employee> getAllEmployees();
    
    Employee getEmployeeById(Long id);
    
    Employee saveEmployee(Employee employee);
    
    Employee updateEmployee(Long id, Employee employeeDetails);

    Employee patchEmployee(Long id, Map<String, Object> updates); // PATCH
    
    void deleteEmployee(Long id);
}