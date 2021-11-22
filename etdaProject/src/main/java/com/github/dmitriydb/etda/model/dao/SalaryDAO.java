package com.github.dmitriydb.etda.model.dao;


import com.github.dmitriydb.etda.model.simplemodel.domain.Salary;
import org.hibernate.query.Query;

import java.util.List;

public class SalaryDAO extends AbstractDAO {
    public void deleteSalaryByEmployeeID(Long employeeNumber){
        startOperation();
        Query query = session.createQuery("FROM Salary WHERE salaryOrder.employeeNumber = :number ORDER BY salaryOrder.fromDate DESC");
        query.setMaxResults(1);
        query.setParameter("number", employeeNumber);
        List<Salary> result = query.getResultList();
        for (Salary s : result)
            session.delete(s);
        endOperation();
    }
}
