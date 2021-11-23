package com.github.dmitriydb.etda.model.dao;

import com.github.dmitriydb.etda.model.simplemodel.domain.Department;
import com.github.dmitriydb.etda.model.simplemodel.domain.DepartmentEmployee;
import com.github.dmitriydb.etda.model.simplemodel.domain.DepartmentEmployeeSuite;
import org.hibernate.query.Query;

import java.util.List;

/**
 * DAO для класса DepartmentEmployee
 *
 * @version 0.1
 * @since 0.1
 */
public class DepartmentEmployeeDAO extends AbstractDAO{

    /**
     * Удаляет последнюю по дате (fromDate) запись из таблицы dept_emp
     * имеющую соответствующий номер сотрудника
     * @param employeeNumber
     */
    public void deleteDepartmentEmployeeByEmpNumber(Long employeeNumber){
        startOperation();
        Query query = session.createQuery("FROM DepartmentEmployee WHERE departmentEmployeeSuite.employeeNumber = :number ORDER BY departmentEmployeeSuite.fromDate DESC");
        query.setParameter("number", employeeNumber);
        query.setMaxResults(1);
        List<DepartmentEmployee> resultSet = query.getResultList();
        for (DepartmentEmployee e : resultSet){
            session.delete(e);
        }
        endOperation();
    }
}
