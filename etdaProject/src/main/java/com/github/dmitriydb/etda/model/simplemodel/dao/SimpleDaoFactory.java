package com.github.dmitriydb.etda.model.simplemodel.dao;

import com.github.dmitriydb.etda.model.EtdaDaoFactory;
import com.github.dmitriydb.etda.model.dao.SimpleDAO;


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

}
