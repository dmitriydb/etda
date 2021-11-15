package com.github.dmitriydb.etda.model.simplemodel.dao;

import com.github.dmitriydb.etda.model.EtdaDaoFactory;
import com.github.dmitriydb.etda.model.dao.AbstractDAO;

public class SimpleDaoFactory extends EtdaDaoFactory {


    @Override
    public AbstractDAO getAbstractDAO() {
        return new AbstractDAO();
    }

    @Override
    public SimpleTitleDAO getTitleDAO() {
        return null;
    }
}
