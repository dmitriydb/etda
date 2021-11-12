package com.github.dmitriydb.etda.model.simplemodel.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

/**
 * Этот класс инкапсулирует назначение зарплаты сотруднику, начиная с определенной даты
 * Используется в таблице salaries
 */
@Embeddable
public class SalaryOrder implements Serializable {

    @Column(name="emp_no", nullable = false)
    private Long employeeNumber;

    @Column(name="from_date", nullable = false)
    private Date fromDate;

    public SalaryOrder() {
    }

    public Long getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(Long employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SalaryOrder that = (SalaryOrder) o;
        return employeeNumber.equals(that.employeeNumber) && fromDate.equals(that.fromDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeNumber, fromDate);
    }

    @Override
    public String toString() {
        return "SalaryOrder{" +
                "employeeNumber=" + employeeNumber +
                ", fromDate=" + fromDate +
                '}';
    }
}
