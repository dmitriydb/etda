package com.github.dmitriydb.etda.model;

import com.github.dmitriydb.etda.model.dao.AbstractDAO;
import com.github.dmitriydb.etda.model.simplemodel.dao.SimpleTitleDAO;

public abstract class EtdaDaoFactory {

    public abstract AbstractDAO getAbstractDAO();
    public abstract SimpleTitleDAO getTitleDAO();
}
