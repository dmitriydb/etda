package com.github.dmitriydb.etda.model.simplemodel.dao;

import com.github.dmitriydb.etda.model.EtdaDaoFactory;
import com.github.dmitriydb.etda.model.dao.*;
import com.github.dmitriydb.etda.model.dao.UserDAO;


/**
 * Простая фабрика DAO
 *
 * @version 0.1
 * @since 0.1
 */
public class SimpleDaoFactory extends EtdaDaoFactory {

    /**
     * @since 0.1
     */
    @Override
    public SimpleDAO getSimpleDAO(Class clazz) {
        return new SimpleDAO(clazz);
    }

    /**
     * @since 0.1
     */
    @Override
    public SimpleTitleDAO getTitleDAO() {
        return new SimpleTitleDAO();
    }

    @Override
    public DepartmentEmployeeDAO getDepartmentEmployeeDAO() {
        return new DepartmentEmployeeDAO();
    }

    @Override
    public DepartmentManagerDAO getDepartmentManagerDAO() {
        return new DepartmentManagerDAO();
    }

    @Override
    public SalaryDAO getSalaryDAO() {
        return new SalaryDAO();
    }

    @Override
    public TitleDao getTitleDao() {
        return new TitleDao();
    }

    @Override
    public UserDAO getUserDao() {
        return new UserDAO();
    }
}
