package org.example.pretest.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @Column(name = "employeeNumber", nullable = false)
    private Integer id;

    @Column(name = "lastName", nullable = false, length = 50)
    private String lastName;

    @Column(name = "firstName", nullable = false, length = 50)
    private String firstName;

    @Column(name = "extension", nullable = false, length = 10)
    private String extension;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "jobTitle", nullable = false, length = 50)
    private String jobTitle;

    @Column(name = "employee_number", nullable = false)
    private Integer employeeNumber;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName1;

    @Column(name = "job_title", nullable = false, length = 50)
    private String jobTitle1;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName1;

    @Column(name = "office_code", nullable = false, length = 10)
    private String officeCode1;

    @Column(name = "reports_to")
    private Integer reportsTo1;

}