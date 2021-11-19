package com.github.dmitriydb.etda.model.simplemodel.domain;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;


/**
 * Сотрудник фирмы
 * Первичным ключом является номер сотрудника
 * В бизнес ключ входят дата рождения, имя, фамилия и пол
 *
 * @version 0.1
 * @since 0.1
 */
@Entity
@Table(name="employees")
public class Employee {

    @Column(name="emp_no", nullable = false)
    @Id
    @GeneratedValue
    private Long employeeNumber;

    @Column(name="birth_date", nullable = false)
    private Date birth_date;

    @Column(name="first_name", nullable = false)
    private String firstName;

    @Column(name="last_name", nullable = false)
    private String lastName;

    @Column(name="gender", nullable = false)
    private Character gender;

    @Column(name="hire_date", nullable = false)
    private Date hireDate;

    public Employee() {
    }

    public Long getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(Long employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public Date getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(Date birth_date) {
        this.birth_date = birth_date;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Character getGender() {
        return gender;
    }

    public void setGender(Character gender) {
        this.gender = gender;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return birth_date.equals(employee.birth_date) && firstName.equals(employee.firstName) && lastName.equals(employee.lastName) && gender.equals(employee.gender);
    }

    @Override
    public int hashCode() {
        return Objects.hash(birth_date, firstName, lastName, gender);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeNumber=" + employeeNumber +
                ", birth_date=" + birth_date +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender=" + gender +
                ", hireDate=" + hireDate +
                '}';
    }

}
