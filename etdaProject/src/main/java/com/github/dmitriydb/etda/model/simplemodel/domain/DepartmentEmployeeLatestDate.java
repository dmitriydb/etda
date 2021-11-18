package com.github.dmitriydb.etda.model.simplemodel.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "dept_emp_latest_date")
public class DepartmentEmployeeLatestDate {
    @Id
    @Column(name="emp_no", nullable = false)
    private Long employeeNumber;

    @Column(name="from_date", nullable = true)
    private Date fromDate;

    @Column(name="to_date", nullable = true)
    private Date toDate;

    public DepartmentEmployeeLatestDate() {
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

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DepartmentEmployeeLatestDate that = (DepartmentEmployeeLatestDate) o;
        return employeeNumber.equals(that.employeeNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeNumber);
    }

    @Override
    public String toString() {
        return "DepartmetEmployeeLatestDate{" +
                "employeeNumber=" + employeeNumber +
                ", fromDate=" + fromDate +
                ", toDate=" + toDate +
                '}';
    }


}
