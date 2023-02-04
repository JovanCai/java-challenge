package jp.co.axa.apidemo.domain.entities;

import jp.co.axa.apidemo.application.dto.EmployeeDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "salary")
    private Integer salary;

    @Column(name = "department")
    private String department;

    public EmployeeDto toDto() {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(this.id);
        employeeDto.setName(this.name);
        employeeDto.setSalary(this.salary);
        employeeDto.setDepartment(this.department);
        return employeeDto;
    }

}
