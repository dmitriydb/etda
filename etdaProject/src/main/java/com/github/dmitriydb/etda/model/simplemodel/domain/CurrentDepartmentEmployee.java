package com.github.dmitriydb.etda.model.simplemodel.domain;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

/**
 * Класс инкапсулирует информацию о текущем отделе сотрудника
 * Первичным ключом является объект класса departmentEmployeeSuite
 *
 * @version 0.1
 * @since 0.1
 */
@Entity
@Table(name="current_dept_emp")
public class CurrentDepartmentEmployee {

    @Id
    @Embedded
    private DepartmentEmployeeSuite departmentEmployeeSuite;

    @Column(name="from_date", nullable = true)
    private Date fromDate;

    @Column(name="to_date", nullable = true)
    private Date toDate;

    public CurrentDepartmentEmployee() {
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
        CurrentDepartmentEmployee that = (CurrentDepartmentEmployee) o;
        return departmentEmployeeSuite.equals(that.departmentEmployeeSuite) && Objects.equals(fromDate, that.fromDate) && Objects.equals(toDate, that.toDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(departmentEmployeeSuite, fromDate, toDate);
    }

    @Override
    public String toString() {
        return String.format("%20d %20s %20s %20s", departmentEmployeeSuite.getDepartmentId(), departmentEmployeeSuite.getDepartmentId(), fromDate, toDate);
    }
}
