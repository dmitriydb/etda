package com.github.dmitriydb.etda.model;

import com.github.dmitriydb.etda.model.simplemodel.domain.Department;
import com.github.dmitriydb.etda.model.simplemodel.domain.Employee;
import com.github.dmitriydb.etda.model.simplemodel.domain.Salary;

import javax.management.ObjectName;
import java.io.Serializable;
import java.util.List;

/**
 * Абстрактная модель данных
 * @version 0.1
 * @since 0.1
 */
public abstract class EtdaModel {
    public static EtdaModel getSimpleModel(){
        return new SimpleModel();
    }

    public abstract List<Object> findEntities(Class clazz, int maxResults, int offset);

    public abstract List<Object> findEntitiesFiltered(Class clazz, String filter, int maxResults, int offset);

    public abstract List<Object> findEntities(Class clazz);
    
    public abstract List<Object> findList(Class clazz, int maxResults, int offset);

    public abstract boolean createEntity(Class clazz, Object entity);

    public abstract boolean updateEntity(Class clazz, Object entity);

    public abstract boolean deleteEntity(Class clazz, Serializable id);

    public abstract Object getEntity(Class clazz, Serializable id);

}
