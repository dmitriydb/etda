package com.github.dmitriydb.etda.model;

import com.github.dmitriydb.etda.model.dao.SimpleDAO;
import com.github.dmitriydb.etda.model.simplemodel.dao.SimpleTitleDAO;

public abstract class EtdaDaoFactory {

    public SimpleDAO getSimpleDAO(Class clazz){
        return new SimpleDAO(clazz);
    }

    public abstract SimpleTitleDAO getTitleDAO();

}
