package com.github.dmitriydb.etda.model.simplemodel.domain;

import javax.persistence.*;
import java.sql.Date;

/**
 * Назначение должности сотруднику
 * Первичный ключ: объект класса TitleOrder
 * Бизнес ключ: все публичные поля
 *
 * @version 0.1
 * @since 0.1
 */
@Entity
@Table(name="titles")
public class Title {
    @Id
    @Embedded
    private TitleOrder titleOrder;

    @Column(name="to_date", nullable = true)
    private Date toDate;

    public Title() {
    }

    public TitleOrder getTitleOrder() {
        return titleOrder;
    }

    public void setTitleOrder(TitleOrder titleOrder) {
        this.titleOrder = titleOrder;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    @Override
    public String toString() {
        return String.format("%20d %25s %20s %20s", titleOrder.getEmployeeNumber(), titleOrder.getTitle(), titleOrder.getFromDate(), toDate);
    }
}
