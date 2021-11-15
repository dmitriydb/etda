package com.github.dmitriydb.etda.model;

import com.github.dmitriydb.etda.model.simplemodel.dao.SimpleDaoFactory;

public class SimpleModel extends EtdaModel{
    @Override
    public EtdaDaoFactory getDaoFactory() {
        return new SimpleDaoFactory();
    }
}
