package jp.co.axa.apidemo.application.services;

import jp.co.axa.apidemo.application.dto.EmployeeDto;
import jp.co.axa.apidemo.domain.entities.Employee;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;

public interface EmployeeService {
    long getTotal(Example<Employee> of);

    Page<Employee> retrieveEmployees(Example<Employee> of, int page);

    Example<Employee> generateQueryParam(String name);

    EmployeeDto getEmployee(Long employeeId);

    void saveEmployee(Employee employee);

    void deleteEmployee(Long employeeId);

    void updateEmployee(Employee employee);
}