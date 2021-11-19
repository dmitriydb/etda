package com.github.dmitriydb.etda.model;

import com.github.dmitriydb.etda.model.simplemodel.dao.SimpleDaoFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.List;

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
    public boolean createEntity(Class clazz, Object entity) {
        try{
            daoFactory.getSimpleDAO(clazz).create(entity);
            return true;
        }
        catch (Exception ex){
            logger.error(ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateEntity(Class clazz, Object entity) {
        try{
            daoFactory.getSimpleDAO(clazz).update(entity);
            return true;
        }
        catch (Exception ex){
            logger.error(ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteEntity(Class clazz, Serializable id) {
        try{
            daoFactory.getSimpleDAO(clazz).delete(id);
            return true;
        }
        catch (Exception ex){
            logger.error(ex.getMessage());
            return false;
        }
    }

    @Override
    public Object getEntity(Class clazz, Serializable id) {
        return daoFactory.getSimpleDAO(clazz).read(id);
    }
}


