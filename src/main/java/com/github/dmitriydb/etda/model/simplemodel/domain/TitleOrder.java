package com.github.dmitriydb.etda.model.simplemodel.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

/**
 * Этот класс инкапсулирует назначение должности сотруднику (начиная с определенной даты)
 * Используется в таблице titles
 *
 * @version 0.1
 * @since 0.1
 */
@Embeddable
public class TitleOrder implements Serializable {

    @Column(name="emp_no", nullable = false)
    private Long employeeNumber;

    @Size(min = 2, max = 50)
    @Column(name="title", nullable = false)
    private String title;

    @Column(name="from_date", nullable = false)
    private Date fromDate;

    public TitleOrder() {
    }

    public Long getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(Long employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
        TitleOrder that = (TitleOrder) o;
        return employeeNumber.equals(that.employeeNumber) && title.equals(that.title) && fromDate.equals(that.fromDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeNumber, title, fromDate);
    }

    @Override
    public String toString() {
        return "TitleOrder{" +
                "employeeNumber=" + employeeNumber +
                ", title='" + title + '\'' +
                ", fromDate=" + fromDate +
                '}';
    }
}
