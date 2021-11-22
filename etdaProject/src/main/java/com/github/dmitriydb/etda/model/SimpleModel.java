package com.github.dmitriydb.etda.model;

import com.github.dmitriydb.etda.model.simplemodel.dao.SimpleDaoFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.List;

/**
 * Простая модель данных, использующая в своей работе
 *
 * @version 0.1
 * @since 0.1
 */
public class SimpleModel extends EtdaModel{

    private EtdaDaoFactory daoFactory = new SimpleDaoFactory();

    private static final Logger logger = LoggerFactory.getLogger(SimpleDaoFactory.class);

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

    @Override
    public void createEntity(Class clazz, Object entity) {
            daoFactory.getSimpleDAO(clazz).create(entity);
    }

    @Override
    public boolean updateEntity(Class clazz, Object entity) {
            daoFactory.getSimpleDAO(clazz).update(entity);
            return true;
    }

    @Override
    public void deleteEntity(Class clazz, Serializable id) {
            daoFactory.getSimpleDAO(clazz).delete(id);
    }

    @Override
    public Object getEntity(Class clazz, Serializable id) {
        return daoFactory.getSimpleDAO(clazz).read(id);
    }
}


