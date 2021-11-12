package com.github.dmitriydb.etda.model.simplemodel.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="departments")
public class Department {
    @Id
    @Column(name="dept_no", nullable = false)
    private String departmentId;

    @Column(name="dept_name", nullable = false, unique = true)
    private String name;

    public Department() {
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return departmentId.equals(that.departmentId) && name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(departmentId, name);
    }

    @Override
    public String toString() {
        return "Department{" +
                "departmentId='" + departmentId + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
