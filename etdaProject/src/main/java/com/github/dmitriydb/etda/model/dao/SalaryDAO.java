package com.github.dmitriydb.etda.model.dao;


import com.github.dmitriydb.etda.model.simplemodel.domain.Salary;
import org.hibernate.query.Query;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * DAO дял класса Salary
 */
public class SalaryDAO extends AbstractDAO {

    /**
     * Находит и удаляет последнюю (по дате from_date) запись о зарплате для сотрудника с номером employeeNumber
     * @param employeeNumber
     */
    public void deleteSalaryByEmployeeID(Long employeeNumber){
        startOperation();
        Query query = session.createQuery("FROM Salary WHERE salaryOrder.employeeNumber = :number ORDER BY salaryOrder.fromDate DESC");
        query.setMaxResults(1);
        query.setParameter("number", employeeNumber);
        List<Salary> result = query.getResultList();
        if (result.size() == 0)
            throw new NoSuchElementException();
        for (Salary s : result)
            session.delete(s);
        endOperation();
    }
}
