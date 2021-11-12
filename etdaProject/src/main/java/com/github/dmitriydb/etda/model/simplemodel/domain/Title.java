package com.github.dmitriydb.etda.model.simplemodel.domain;

import javax.persistence.*;
import java.sql.Date;

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
        return "Title{" +
                "titleOrder=" + titleOrder +
                ", toDate=" + toDate +
                '}';
    }
}
