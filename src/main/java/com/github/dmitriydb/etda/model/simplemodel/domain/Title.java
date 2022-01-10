package com.github.dmitriydb.etda.model.simplemodel.domain;

import com.github.dmitriydb.etda.model.EtdaEntity;
import com.github.dmitriydb.etda.model.LocaleManager;

import javax.persistence.*;
import javax.validation.Valid;
import java.sql.Date;
import java.util.Locale;

/**
 * Назначение должности сотруднику
 * Первичный ключ: объект класса TitleOrder
 * Бизнес ключ: все публичные поля
 *
 * @version 0.1.2
 * @since 0.1
 */
@Entity
@Table(name="titles")
public class Title implements EtdaEntity {
    @Id
    @Embedded
    @Valid
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
        try{
            return String.format("%20d %25s %20s %20s", titleOrder.getEmployeeNumber(), titleOrder.getTitle(), titleOrder.getFromDate(), toDate);
        }
        catch (Exception ex){
            return "NOT SET";
        }

    }

    public String format(Locale locale){
        return String.format("%20d %25s %20s %20s", titleOrder.getEmployeeNumber(), titleOrder.getTitle(),
                LocaleManager.formatSqlDateToLocaleFormat(titleOrder.getFromDate(), locale),
                LocaleManager.formatSqlDateToLocaleFormat(toDate, locale));
    }

}
