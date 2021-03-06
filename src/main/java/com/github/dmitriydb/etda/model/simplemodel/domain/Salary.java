package com.github.dmitriydb.etda.model.simplemodel.domain;

import com.github.dmitriydb.etda.model.EtdaEntity;
import com.github.dmitriydb.etda.model.LocaleManager;
import net.bytebuddy.asm.Advice;

import javax.persistence.*;
import java.sql.Date;
import java.util.Locale;
import java.util.Objects;

/**
 * Запись о назначении зарплаты сотруднику
 * Первичным ключом является объект класса SalaryOrder,
 * В бизнес-ключ входят все публичные поля
 *
 * @version 0.1.2
 * @since 0.1
 */
@Entity
@Table(name="salaries")
public class Salary implements EtdaEntity {

    @Id
    @Embedded
    private SalaryOrder salaryOrder;

    @Column(name="salary", nullable = false)
    private Long salary;

    @Column(name="to_date", nullable = false)
    private Date toDate;

    public Salary() {
    }

    public SalaryOrder getSalaryOrder() {
        return salaryOrder;
    }

    public void setSalaryOrder(SalaryOrder salaryOrder) {
        this.salaryOrder = salaryOrder;
    }

    public Long getSalary() {
        return salary;
    }

    public void setSalary(Long salary) {
        this.salary = salary;
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
        Salary salary1 = (Salary) o;
        return salaryOrder.equals(salary1.salaryOrder) && salary.equals(salary1.salary) && toDate.equals(salary1.toDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(salaryOrder, salary, toDate);
    }

    @Override
    public String toString() {
        try{
            return String.format("%20d %20d %20s %20s", salaryOrder.getEmployeeNumber(), salary, salaryOrder.getFromDate(), toDate);
        }
        catch (Exception ex){
            return "NOT SET";
        }
    }

    public String format(Locale locale){
        return String.format("%20d %20s %20s %20s", salaryOrder.getEmployeeNumber(),
                LocaleManager.formatSalaryToLocaleFormat(salary, locale),
                LocaleManager.formatSqlDateToLocaleFormat(salaryOrder.getFromDate(), locale),
                LocaleManager.formatSqlDateToLocaleFormat(toDate, locale)
        );
    }
}
