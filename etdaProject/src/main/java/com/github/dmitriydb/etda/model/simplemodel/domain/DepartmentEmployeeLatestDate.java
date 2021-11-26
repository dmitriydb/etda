package com.github.dmitriydb.etda.model.simplemodel.domain;

import com.github.dmitriydb.etda.model.EtdaEntity;
import com.github.dmitriydb.etda.model.LocaleManager;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;
import java.util.Locale;
import java.util.Objects;

/**
 * Класс инкапсулирует последнее принятие сотрудника на работу в отдел
 * Первичный ключ: номер сотрудника
 * Бизнес ключ: номер сотрудника
 *
 * В теории в БД количество строк в этой таблице должно совпадать с количеством сотрудников (?)
 *
 * @version 0.1.2
 * @since 0.1
 */
@Entity
@Table(name = "dept_emp_latest_date")
public class DepartmentEmployeeLatestDate implements EtdaEntity {
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
        return String.format("%20d %20s %20s", employeeNumber, fromDate, toDate);
    }

    public String format(Locale locale){
        return String.format("%20d %20s %20s", employeeNumber,
                LocaleManager.formatSqlDateToLocaleFormat(fromDate, locale),
                LocaleManager.formatSqlDateToLocaleFormat(toDate, locale)
        );
    }

}
