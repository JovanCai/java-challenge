package jp.co.axa.apidemo.application.serviceImpl;

import jp.co.axa.apidemo.application.dto.EmployeeDto;
import jp.co.axa.apidemo.application.services.EmployeeService;
import jp.co.axa.apidemo.domain.entities.Employee;
import jp.co.axa.apidemo.domain.repositories.EmployeeRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public long getTotal(Example<Employee> of) {
        return employeeRepository.count(of);
    }

    public Page<Employee> retrieveEmployees(Example<Employee> of, int page) {
        Page<Employee> employees = employeeRepository.findAll(of, PageRequest.of(page - 1, 5));
        return employees;
    }

    public Example<Employee> generateQueryParam(String name) {
        Employee employee = new Employee();
        employee.setName(name);
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains());
        return Example.of(employee, exampleMatcher);
    }

    public EmployeeDto getEmployee(Long employeeId) {
        Optional<EmployeeDto> optEmp = employeeRepository.findById(employeeId).map(Employee::toDto);
        return optEmp.get();
    }

    public void saveEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    public void deleteEmployee(Long employeeId) {
        employeeRepository.deleteById(employeeId);
    }

    public void updateEmployee(Employee employee) {
        employeeRepository.save(employee);
    }
}