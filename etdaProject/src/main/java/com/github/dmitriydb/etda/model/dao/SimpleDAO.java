package com.github.dmitriydb.etda.model.dao;

import com.github.dmitriydb.etda.model.simplemodel.domain.Employee;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Простой DAO-класс, единообразно работающий с сущностями всех типов
 * Инициализируется объектом типа Class в конструкторе и в дальнейшем работает только с этим классом
 *
 * @version 0.1
 * @since 0.1
 */
public class SimpleDAO extends AbstractDAO {
    private Class clazz;
    private static final Logger logger = LoggerFactory.getLogger(SimpleDAO.class);

    public SimpleDAO(Class clazz){
        this.clazz = clazz;
    }

    public void create(Object e) {
        logger.debug("Object {}", e);
        startOperation();
        session.save(e);
        logger.debug("Object {} was successfully created", e.toString());
        endOperation();
    }

    public Object read(Serializable id) {
        startOperation();
        try{
            Object e = session.get(clazz, id);
            return e;
        }
        catch (Exception ex){
            logger.error("Error: ", ex);
            return null;
        }
        finally {
            endOperation();
        }
    }

    public void update(Object e) {
        logger.debug("Updating {}", e);
        try{
        startOperation();
        logger.debug("Updating an object to {}", e.toString());
        session.update(e);
        logger.debug("Object was successfully updated: {}", e.toString());
        endOperation();
        }
        catch (Exception ex){
            logger.error("Error", ex);
        }
    }


    public void delete(Serializable id) throws IllegalArgumentException{
        logger.debug("Trying to delete entity with id {}", id);
        startOperation();
        Object e = session.get(clazz, id);
        session.delete(e);
        logger.debug("Object {} was successfully deleted", e.toString());
        endOperation();
    }

    /**
     * Возвращает частичный список сущностей из полной выборки по сущности
     * @param howMuch максимальный размер выборки
     * @param offset смещение выборки относительно 1 позиции
     * @return
     *
     * @since 0.1
     */
    public List<Object> readList(int howMuch, int offset){
        startOperation();
        try{
            Query query = session.createQuery("FROM " + clazz.getSimpleName());
            query.setMaxResults(howMuch);
            query.setFirstResult(offset);
            List<Object> result = query.getResultList();
            if (result == null) return new ArrayList<>();
            return result;
        }
        catch (Exception ex){
            logger.error("Error occurred while reading list: {}", ex.getMessage());
            return null;
        }
        finally {
            endOperation();
        }

    }

    /**
     * Возвращает отфильтрованный список сущностей из полной выборки по сущности
     * @param maxResults максимальный размер выборки
     * @param offset смещение выборки относительно 1 позиции
     * @param filter строка, по которой производится фильтрация выборки
     * @return
     *
     * @since 0.1
     */

    public List<Object> readFilteredList(String filter, int maxResults, int offset) {
        startOperation();
        try{
            logger.debug("filter = {}", filter);
            javax.persistence.Query query = session.createQuery("FROM " + clazz.getSimpleName() + " " + DaoFiltersStrings.DAO_FILTERS.get(clazz));
            if (DaoFiltersStrings.DAO_FILTERS.get(clazz).contains(":filter"))
                query.setParameter("filter", "%" + filter + "%");
            if (DaoFiltersStrings.DAO_FILTERS.get(clazz).contains(":number")){
                try{
                    Long x = Long.valueOf(filter);
                    query.setParameter("number", x);
                }
                catch (NumberFormatException ex){
                    query.setParameter("number", -1L);
                }
            }

            query.setMaxResults(maxResults);
            query.setFirstResult(offset);
            List<Object> result = query.getResultList();
            if (result == null) return new ArrayList<>();
            return result;
        }
        catch (Exception ex){
            logger.error("{}", ex.getMessage());
            return new ArrayList<>();
        }
        finally {
            endOperation();
        }
    }

    /**
     * Возвращает список всех сущностей класса, которым было инициализировано DAO
     * @return список всех сущностей класса, которым было инициализировано DAO
     * @since 0.1
     */
    public List<Object> findAll() {
        startOperation();
        try{
            javax.persistence.Query query = session.createQuery("FROM " + clazz.getSimpleName());
            List<Object> result = query.getResultList();
            if (result == null) return new ArrayList<>();
            return result;
        }
        catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
        finally {
            endOperation();
        }
    }

    public long countEntities(){
        startOperation();
        try{
            javax.persistence.Query query = session.createQuery("SELECT COUNT (*) FROM " + clazz.getSimpleName());
            long result = (long)query.getSingleResult();
            return result;

        }
        catch (Exception ex){
            ex.printStackTrace();
            return 0;
        }
        finally {
            endOperation();
        }
    }

    public long countEntitiesFiltered(String filter){
        startOperation();
        try{
            logger.debug("filter = {}", filter);
            javax.persistence.Query query = session.createQuery("SELECT COUNT(*) FROM " + clazz.getSimpleName() + " " + DaoFiltersStrings.DAO_FILTERS.get(clazz));
            if (DaoFiltersStrings.DAO_FILTERS.get(clazz).contains(":filter"))
                query.setParameter("filter", "%" + filter + "%");
            if (DaoFiltersStrings.DAO_FILTERS.get(clazz).contains(":number")){
                try{
                    Long x = Long.valueOf(filter);
                    query.setParameter("number", x);
                }
                catch (NumberFormatException ex){
                    query.setParameter("number", -1L);
                }
            }
            long result = (long) query.getSingleResult();
            return result;
        }
        catch (Exception ex){
            logger.error("{}", ex.getMessage());
            return 0;
        }
        finally {
            endOperation();
        }
    }
}
