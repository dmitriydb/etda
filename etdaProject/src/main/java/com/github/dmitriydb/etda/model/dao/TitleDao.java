package com.github.dmitriydb.etda.model.dao;

import com.github.dmitriydb.etda.model.simplemodel.domain.Salary;
import com.github.dmitriydb.etda.model.simplemodel.domain.Title;
import org.hibernate.query.Query;

import java.util.List;
import java.util.NoSuchElementException;


/**
 * DAO для класса Title
 */
public class TitleDao extends AbstractDAO {

    /**
     * Находит и удаляет последнюю (по дате from_date) запись о должности для сотрудника с номером employeeNumber
     * @param employeeNumber
     */
    public void deleteTitleByEmployeeID(Long employeeNumber){
        startOperation();
        Query query = session.createQuery("FROM Title WHERE titleOrder.employeeNumber = :number ORDER BY titleOrder.fromDate DESC");
        query.setParameter("number", employeeNumber);
        query.setMaxResults(1);
        List<Title> result = query.getResultList();
        if (result.size() == 0)
            throw new NoSuchElementException();
        for (Title s : result)
            session.delete(s);
        endOperation();
    }
}
