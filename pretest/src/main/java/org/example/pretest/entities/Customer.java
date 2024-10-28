package org.example.pretest.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "customers")
public class Customer {
    @Id // primary
    @Column(name = "customerNumber", nullable = false)
    private Integer id;

    @Column(name = "customerName", nullable = false, length = 50)
    private String customerName;

    @Column(name = "contactLastName", nullable = false, length = 50)
    private String contactLastName;

    @Column(name = "contactFirstName", nullable = false, length = 50)
    private String contactFirstName;

    @Column(name = "phone", nullable = false, length = 50)
    private String phone;

    @Column(name = "addressLine1", nullable = false, length = 50)
    private String addressLine1;

    @Column(name = "addressLine2", length = 50)
    private String addressLine2;

    @Column(name = "city", nullable = false, length = 50)
    private String city;

    @Column(name = "state", length = 50)
    private String state;

    @Column(name = "postalCode", length = 15)
    private String postalCode;

    @Column(name = "country", nullable = false, length = 50)
    private String country;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "salesRepEmployeeNumber")
    private Employee salesRepEmployeeNumber;

    @Column(name = "creditLimit", precision = 10, scale = 2)
    private BigDecimal creditLimit;

    @Column(name = "customer_number", nullable = false)
    private Integer customerNumber;

    @Column(name = "address_line1", nullable = false, length = 50)
    private String addressLine11;

    @Column(name = "address_line2", length = 50)
    private String addressLine21;

    @Column(name = "contact_first_name", nullable = false, length = 50)
    private String contactFirstName1;

    @Column(name = "contact_last_name", nullable = false, length = 50)
    private String contactLastName1;

    @Column(name = "credit_limit", precision = 10, scale = 2)
    private BigDecimal creditLimit1;

    @Column(name = "customer_name", nullable = false, length = 50)
    private String customerName1;

    @Column(name = "postal_code", length = 15)
    private String postalCode1;

    @Column(name = "sales_rep_employee_number")
    private Integer salesRepEmployeeNumber1;

    @OneToMany(mappedBy = "customerNumber")
    private Set<Order> orders = new LinkedHashSet<>();

    @OneToMany(mappedBy = "customerNumber")
    private Set<Payment> payments = new LinkedHashSet<>();

}