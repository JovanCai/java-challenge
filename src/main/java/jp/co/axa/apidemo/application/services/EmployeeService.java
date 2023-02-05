package jp.co.axa.apidemo.application.services;

import jp.co.axa.apidemo.application.dto.EmployeeDto;
import jp.co.axa.apidemo.domain.entities.Employee;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;

public interface EmployeeService {
    // Get the amount of employees
    long getTotal(Example<Employee> of);

    // Get employees info on this page
    Page<Employee> retrieveEmployees(Example<Employee> of, int page);

    // Generate the parameter of getting employee
    Example<Employee> generateQueryParam(String name);

    // Get 1 employee info
    EmployeeDto getEmployee(Long employeeId);

    // Save an employee
    void saveEmployee(Employee employee);

    // Delete an employee
    void deleteEmployee(Long employeeId);

    // Update an employee
    void updateEmployee(Employee employee);
}