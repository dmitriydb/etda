package com.github.dmitriydb.etda.model;

import com.github.dmitriydb.etda.model.dao.*;
import com.github.dmitriydb.etda.model.simplemodel.dao.SimpleTitleDAO;
import com.github.dmitriydb.etda.model.dao.UserDAO;

/**
 * Абстрактная фабрика DAO
 *
 * @version 0.1.1
 * @since 0.1
 */
public abstract class EtdaDaoFactory {

    public SimpleDAO getSimpleDAO(Class clazz){
        return new SimpleDAO(clazz);
    }

    public abstract SimpleTitleDAO getTitleDAO();

    public abstract DepartmentEmployeeDAO getDepartmentEmployeeDAO();

    public abstract DepartmentManagerDAO getDepartmentManagerDAO();

    public abstract SalaryDAO getSalaryDAO();

    public abstract TitleDao getTitleDao();

    public abstract UserDAO getUserDao();
}
