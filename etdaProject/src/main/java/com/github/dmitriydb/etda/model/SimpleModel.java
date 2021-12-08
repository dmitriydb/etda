package com.github.dmitriydb.etda.model;

import com.github.dmitriydb.etda.model.dao.DepartmentManagerDAO;
import com.github.dmitriydb.etda.model.simplemodel.dao.SimpleDaoFactory;
import com.github.dmitriydb.etda.model.simplemodel.domain.*;
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
public class SimpleModel extends EtdaModel {

    private EtdaDaoFactory daoFactory = new SimpleDaoFactory();

    private static final Logger logger = LoggerFactory.getLogger(SimpleDaoFactory.class);

    public SimpleModel() {

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
        if (clazz.equals(DepartmentEmployee.class))
            daoFactory.getDepartmentEmployeeDAO().deleteDepartmentEmployeeByEmpNumber((Long) id);
        else if (clazz.equals(DepartmentManager.class)) {
            if (id instanceof DepartmentEmployeeSuite)
                daoFactory.getSimpleDAO(clazz).delete(id);
            else
                daoFactory.getDepartmentManagerDAO().deleteManagerByEmployeeNumber((Long) id);
        } else if (clazz.equals(Salary.class)) {
            if (id instanceof SalaryOrder)
                daoFactory.getSimpleDAO(clazz).delete(id);
            else
                daoFactory.getSalaryDAO().deleteSalaryByEmployeeID((Long) id);
        } else if (clazz.equals(Title.class)) {
            if (id instanceof TitleOrder)
                daoFactory.getSimpleDAO(clazz).delete(id);
            else
                daoFactory.getTitleDao().deleteTitleByEmployeeID((Long) id);
        } else
            daoFactory.getSimpleDAO(clazz).delete(id);
    }

    @Override
    public Object getEntity(Class clazz, Serializable id) {
        return daoFactory.getSimpleDAO(clazz).read(id);
    }

    @Override
    public long countEntitiesFiltered(Class clazz, String filter) {
        return daoFactory.getSimpleDAO(clazz).countEntitiesFiltered(filter);
    }

    @Override
    public long countEntities(Class clazz) {
        return daoFactory.getSimpleDAO(clazz).countEntities();
    }
}


