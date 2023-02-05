package jp.co.axa.apidemo.application.dto;

import lombok.Data;

/**
 * Dto of employee
 */
@Data
public class EmployeeDto {
    private Long id;
    private String name;
    private Integer salary;
    private String department;
}
