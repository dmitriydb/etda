package com.github.dmitriydb.etda.model.simplemodel.domain;

import javax.persistence.*;
import java.util.Locale;
import java.util.Objects;

/**
 * Отдел фирмы
 * Первичный ключ - ID отдела (строка)
 * Бизнес ключ - ID и наименование отдела
 *
 * @version 0.1.2
 * @since 0.1
 */
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
        return String.format("%20s %30s", departmentId, name);
    }

    public String format(Locale locale){
        return String.format("%20s %30s", departmentId, name);
    }
}
