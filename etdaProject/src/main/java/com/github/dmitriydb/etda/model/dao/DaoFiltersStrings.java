package com.github.dmitriydb.etda.model.dao;

import com.github.dmitriydb.etda.model.simplemodel.domain.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс, который содержит строки для HQL-оператора WHERE для фильтра сущностей в модели
 * Данные строки просто добавляются в конец запроса
 * Используется в классе SimpleDAO {@link com.github.dmitriydb.etda.model.dao.SimpleDAO} чтобы фильтровать сущности типа Object единоообразным способом
 *
 * Список имен параметров, которые используются в фильтрах:
 * filter - обычная строка
 * number - число
 *
 * Возможно в будущем понадобится фильтровать сущности по дате, но лучше прибегнуть к специфическим классам для отдельных фильтров
 *
 * @version 0.1
 * @since 0.1
 */
public class DaoFiltersStrings {
    /**
     * Карта, которая содержит соответствие строки поиска классу сущности
     */
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
