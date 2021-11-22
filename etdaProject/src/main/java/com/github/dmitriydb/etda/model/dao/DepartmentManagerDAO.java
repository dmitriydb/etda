package com.github.dmitriydb.etda.model.dao;

import com.github.dmitriydb.etda.model.simplemodel.domain.DepartmentEmployee;
import com.github.dmitriydb.etda.model.simplemodel.domain.DepartmentManager;
import org.hibernate.query.Query;

import java.util.List;

public class DepartmentManagerDAO extends AbstractDAO{
    public void deleteManagerByEmployeeNumber(Long employeeNumber){
        startOperation();
        Query query = session.createQuery("FROM DepartmentManager WHERE departmentManagerSuite.employeeNumber = :number ORDER BY departmentManagerSuite.fromDate DESC");
        query.setParameter("number", employeeNumber);
        query.setMaxResults(1);
        List<DepartmentManager> resultSet = query.getResultList();
        for (DepartmentManager e : resultSet){
            session.delete(e);
        }
        endOperation();
    }
}
