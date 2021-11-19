package com.github.dmitriydb.etda.model;

import com.github.dmitriydb.etda.model.dao.SimpleDAO;
import com.github.dmitriydb.etda.model.simplemodel.dao.SimpleTitleDAO;

/**
 * Абстрактная фабрика DAO
 *
 * @version 0.1
 * @since 0.1
 */
public abstract class EtdaDaoFactory {

    public SimpleDAO getSimpleDAO(Class clazz){
        return new SimpleDAO(clazz);
    }

    public abstract SimpleTitleDAO getTitleDAO();

}
