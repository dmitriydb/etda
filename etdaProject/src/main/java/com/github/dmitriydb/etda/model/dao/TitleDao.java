package com.github.dmitriydb.etda.model.dao;

import com.github.dmitriydb.etda.model.simplemodel.domain.Salary;
import com.github.dmitriydb.etda.model.simplemodel.domain.Title;
import org.hibernate.query.Query;

import java.util.List;

public class TitleDao extends AbstractDAO {
    public void deleteTitleByEmployeeID(Long employeeNumber){
        startOperation();
        Query query = session.createQuery("FROM Title WHERE titleOrder.employeeNumber = :number ORDER BY titleOrder.fromDate DESC");
        query.setParameter("number", employeeNumber);
        query.setMaxResults(1);
        List<Title> result = query.getResultList();
        for (Title s : result)
            session.delete(s);
        endOperation();
    }
}
