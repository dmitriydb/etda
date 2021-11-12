package com.github.dmitriydb.etda.model.simplemodel.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
/**
 * Этот класс инкапсулирует связку между номером отдела и номером сотрудника данного отдела
 * Используется в таблице dept_manager и dept_emp
 */
public class DepartmentEmployeeSuite implements Serializable {

    @Column(name="emp_no", nullable = false)
    private Long employeeNumber;

    @Column(name="dept_no", nullable = false)
    private String departmentId;

    public DepartmentEmployeeSuite() {
    }

    public Long getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(Long employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DepartmentEmployeeSuite that = (DepartmentEmployeeSuite) o;
        return employeeNumber.equals(that.employeeNumber) && departmentId.equals(that.departmentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeNumber, departmentId);
    }

    @Override
    public String toString() {
        return "DepartmentManagerSuite{" +
                "employeeNumber=" + employeeNumber +
                ", departmentId='" + departmentId + '\'' +
                '}';
    }
}
