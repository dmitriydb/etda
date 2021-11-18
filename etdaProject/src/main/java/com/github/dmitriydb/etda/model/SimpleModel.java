package com.github.dmitriydb.etda.model;

import com.github.dmitriydb.etda.model.simplemodel.dao.SimpleDaoFactory;

import java.util.List;

public class SimpleModel extends EtdaModel{

    private EtdaDaoFactory daoFactory = new SimpleDaoFactory();

    SimpleModel(){

    }

    @Override
    public List<Object> findList(Class clazz, int maxResults, int offset) {
        return daoFactory.getSimpleDAO(clazz).readList(maxResults, offset);
    }

    @Override
    public List<Object> findEntities(Class clazz, int maxResults, int offset) {
        return daoFactory.getSimpleDAO(clazz).readList(maxResults, offset);
    }

    @Override
    public List<Object> findEntitiesFiltered(Class clazz, String filter, int maxResults, int offset) {
        return daoFactory.getSimpleDAO(clazz).readFilteredList(filter, maxResults, offset);
    }

    @Override
    public List<Object> findEntities(Class clazz) {
        return daoFactory.getSimpleDAO(clazz).findAll();
    }
}
