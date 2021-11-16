package com.github.dmitriydb.etda.model;

import com.github.dmitriydb.etda.model.simplemodel.dao.SimpleDaoFactory;
import com.github.dmitriydb.etda.model.simplemodel.domain.Department;
import com.github.dmitriydb.etda.model.simplemodel.domain.Employee;

public class SimpleModel extends EtdaModel{
    @Override
    public EtdaDaoFactory getDaoFactory() {
        return new SimpleDaoFactory();
    }

    @Override
    public Class getEmployeeClass() {
        return Employee.class;
    }

    @Override
    public Class getDepartmentClass() {
        return Department.class;
    }
}
