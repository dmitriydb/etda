package com.github.dmitriydb.etda.model.simplemodel.domain;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

/**
 * Менеджер отдела
 * Первичный ключ - объект класса DepartmentEmployeeSuite
 * Бизнес ключ - все публичные поля
 *
 * @version 0.1
 * @since 0.1
 */
@Entity
@Table(name="dept_manager")
public class DepartmentManager {
    @Id
    @Embedded
    private DepartmentEmployeeSuite departmentManagerSuite;

    @Column(name="from_date", nullable = false)
    private Date fromDate;

    @Column(name="to_date", nullable = false)
    private Date toDate;

    public DepartmentManager() {
    }

    public DepartmentEmployeeSuite getDepartmentManagerSuite() {
        return departmentManagerSuite;
    }

    public void setDepartmentManagerSuite(DepartmentEmployeeSuite departmentManagerSuite) {
        this.departmentManagerSuite = departmentManagerSuite;
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
        DepartmentManager that = (DepartmentManager) o;
        return departmentManagerSuite.equals(that.departmentManagerSuite) && fromDate.equals(that.fromDate) && toDate.equals(that.toDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(departmentManagerSuite, fromDate, toDate);
    }

    @Override
    public String toString() {
        return String.format("%20d %20s %20s %20s", departmentManagerSuite.getEmployeeNumber(), departmentManagerSuite.getDepartmentId(), fromDate, toDate);
    }
}
