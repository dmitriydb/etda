package com.github.dmitriydb.etda.model;

import com.github.dmitriydb.etda.model.simplemodel.domain.Department;
import com.github.dmitriydb.etda.model.simplemodel.domain.Employee;
import com.github.dmitriydb.etda.model.simplemodel.domain.Salary;

import java.util.List;

public abstract class EtdaModel {
    public static EtdaModel getSimpleModel(){
        return new SimpleModel();
    }


    public abstract List<Object> findEntities(Class clazz, int maxResults, int offset);

    public abstract List<Object> findEntitiesFiltered(Class clazz, String filter, int maxResults, int offset);

    public abstract List<Object> findEntities(Class clazz);
    
    public abstract List<Object> findList(Class clazz, int maxResults, int offset);
}
