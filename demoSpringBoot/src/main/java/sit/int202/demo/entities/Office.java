package sit.int202.demo.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import sit.int202.demo.entities.Employee;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "offices", schema = "classicmodels")
public class Office {
    @Id
    @Column(name = "officeCode", nullable = false, length = 10)
    private String officeCode;

    @Column(name = "city", nullable = false, length = 50)
    private String city;

    @Column(name = "phone", nullable = false, length = 50)
    private String phone;

    @Column(name = "addressLine1", nullable = false, length = 50)
    private String addressLine1;

    @Column(name = "addressLine2", length = 50)
    private String addressLine2;

    @Column(name = "state", length = 50)
    private String state;

    @Column(name = "country", nullable = false, length = 50)
    private String country;

    @Column(name = "postalCode", nullable = false, length = 15)
    private String postalCode;

    @Column(name = "territory", nullable = false, length = 10)
    private String territory;

    @OneToMany(mappedBy = "officeCode")
    private Set<Employee> employees = new LinkedHashSet<>();

}