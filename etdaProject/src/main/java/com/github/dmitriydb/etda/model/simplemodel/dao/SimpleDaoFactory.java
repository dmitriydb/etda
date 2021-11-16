package com.github.dmitriydb.etda.model.simplemodel.dao;

import com.github.dmitriydb.etda.model.EtdaDaoFactory;
import com.github.dmitriydb.etda.model.dao.SimpleDAO;

public class SimpleDaoFactory extends EtdaDaoFactory {


    @Override
    public SimpleDAO getSimpleDAO(Class clazz) {
        return new SimpleDAO(clazz);
    }

    @Override
    public SimpleTitleDAO getTitleDAO() {
        return new SimpleTitleDAO();
    }

    @Override
    public SimpleEmployeeDAO getEmployeeDAO() {
        return new SimpleEmployeeDAO();
    }
}
