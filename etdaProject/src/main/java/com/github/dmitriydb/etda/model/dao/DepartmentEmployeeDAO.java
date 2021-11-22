package com.github.dmitriydb.etda.model.dao;

import com.github.dmitriydb.etda.model.simplemodel.domain.Department;
import com.github.dmitriydb.etda.model.simplemodel.domain.DepartmentEmployee;
import com.github.dmitriydb.etda.model.simplemodel.domain.DepartmentEmployeeSuite;
import org.hibernate.query.Query;

import java.util.List;


public class DepartmentEmployeeDAO extends AbstractDAO{
    public void deleteDepartmentEmployeeByEmpNumber(Long employeeNumber){
        startOperation();
        Query query = session.createQuery("FROM DepartmentEmployee WHERE departmentEmployeeSuite.employeeNumber = :number");
        query.setParameter("number", employeeNumber);
        query.setMaxResults(1);
        List<DepartmentEmployee> resultSet = query.getResultList();
        for (DepartmentEmployee e : resultSet){
            session.delete(e);
        }
        endOperation();
    }
}
