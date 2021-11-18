package com.github.dmitriydb.etda.model.dao;

import com.github.dmitriydb.etda.model.simplemodel.domain.*;

import java.util.HashMap;
import java.util.Map;

public class DaoFiltersStrings {
    public static final Map<Class, String> DAO_FILTERS;

    static {
        DAO_FILTERS = new HashMap<>();
        DAO_FILTERS.put(Employee.class, "WHERE (lastName LIKE :filter OR firstName LIKE :filter) OR (employeeNumber = :number)");
        DAO_FILTERS.put(Salary.class, "WHERE (salaryOrder.employeeNumber = :number) ");
        DAO_FILTERS.put(Title.class, "WHERE (titleOrder.employeeNumber = :number) OR (titleOrder.title LIKE :filter) ");
        DAO_FILTERS.put(CurrentDepartmentEmployee.class, "WHERE (departmentEmployeeSuite.employeeNumber = :number) ");
        DAO_FILTERS.put(DepartmentEmployee.class, "WHERE (departmentEmployeeSuite.employeeNumber = :number) ");
        DAO_FILTERS.put(DepartmentManager.class, "WHERE (departmentManagerSuite.employeeNumber = :number) ");
        DAO_FILTERS.put(DepartmentEmployeeLatestDate.class, "WHERE (employeeNumber = :number) ");
    }
}
