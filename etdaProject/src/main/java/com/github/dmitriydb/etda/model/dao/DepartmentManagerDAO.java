package com.github.dmitriydb.etda.model.dao;

import com.github.dmitriydb.etda.model.simplemodel.domain.DepartmentEmployee;
import com.github.dmitriydb.etda.model.simplemodel.domain.DepartmentManager;
import org.hibernate.query.Query;

import java.util.List;

/**
 * DAO для класса DepartmentManager
 *
 * @version 0.1
 * @since 0.1
 */
public class DepartmentManagerDAO extends AbstractDAO{
    /**
     * Находит и удаляет последнюю (по дате from_date) запись о менеджере с номером employeeNumber
     * @param employeeNumber
     */
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
