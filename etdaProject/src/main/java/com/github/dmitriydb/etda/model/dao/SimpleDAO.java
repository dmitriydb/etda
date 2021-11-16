package com.github.dmitriydb.etda.model.dao;

import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SimpleDAO extends AbstractDAO {
    private Class clazz;
    private static final Logger logger = LoggerFactory.getLogger(SimpleDAO.class);

    public SimpleDAO(Class clazz){
        this.clazz = clazz;
    }

    public boolean create(Object e) {
        startOperation();
        try{
            session.save(e);
            logger.debug("Object {} was successfully created", e.toString());
            return true;
        }
        catch (Exception ex){
            logger.error("Error occurred while creating object {}: {}", e, ex.getMessage());
            return false;
        }
        finally {
            endOperation();
        }
    }


    public Object read(Serializable id) {
        startOperation();
        try{
            Object e = session.get(clazz, id);
            return e;
        }
        catch (Exception ex){
            logger.error("Error occurred while reading object with id {}: {}", id, ex.getMessage());
            return null;
        }
        finally {
            endOperation();
        }

    }


    public boolean update(Object e) {
        startOperation();
        try{
            logger.debug("Updating an object to {}", e.toString());
            session.update(e);
            logger.debug("Object was successfully updated: {}", e.toString());
            return true;
        }
        catch (Exception ex){
            logger.error("Error occurred while updating object {} : {}", e, ex.getMessage());
            return false;
        }
        finally {
            endOperation();
        }
    }


    public boolean delete(Serializable id) {
        startOperation();
        try{
            Object e = session.get(clazz, id);
            session.delete(e);
            logger.debug("Object {} was successfully deleted", e.toString());
            return true;
        }
        catch (Exception ex){
            logger.error("Error occurred while deleting object with id {}: {}", id, ex.getMessage());
            return false;
        }
        finally {
            endOperation();
        }
    }

    public List<Object> readList(int howMuch, int offset){
        startOperation();
        try{
            Query query = session.createQuery("FROM " + clazz.getSimpleName());
            query.setMaxResults(howMuch);
            query.setFirstResult(offset);
            return query.getResultList();
        }
        catch (Exception ex){
            logger.error("Error occurred while reading list: {}", ex.getMessage());
            return null;
        }
        finally {
            endOperation();
        }

    }
}
