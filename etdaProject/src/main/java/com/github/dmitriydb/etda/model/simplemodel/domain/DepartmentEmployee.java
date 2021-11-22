package com.github.dmitriydb.etda.model.simplemodel.domain;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

/**
 * Класс инкапсулирует принятие сотрудника на работу в отдел
 * Первичный ключ: объект класса departmentEmployeeSuite
 * Бизнес ключ: все публичные поля
 *
 * @version 0.1
 * @since 0.1
 */
@Entity
@Table(name="dept_emp")
public class DepartmentEmployee {

    @Id
    @Embedded
    private DepartmentEmployeeSuite departmentEmployeeSuite;

    @Column(name="from_date", nullable = false)
    private Date fromDate;

    @Column(name="to_date", nullable = false)
    private Date toDate;

    public DepartmentEmployee() {
    }

    public DepartmentEmployeeSuite getDepartmentEmployeeSuite() {
        return departmentEmployeeSuite;
    }

    public void setDepartmentEmployeeSuite(DepartmentEmployeeSuite departmentEmployeeSuite) {
        this.departmentEmployeeSuite = departmentEmployeeSuite;
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
        DepartmentEmployee that = (DepartmentEmployee) o;
        return departmentEmployeeSuite.equals(that.departmentEmployeeSuite) && fromDate.equals(that.fromDate) && toDate.equals(that.toDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(departmentEmployeeSuite, fromDate, toDate);
    }

    @Override
    public String toString() {
        return String.format("%20d %20s %20s %20s", departmentEmployeeSuite.getEmployeeNumber(), departmentEmployeeSuite.getDepartmentId(), fromDate, toDate);
    }
}
