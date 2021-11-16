package com.github.dmitriydb.etda.model;

public abstract class EtdaModel {
    public abstract EtdaDaoFactory getDaoFactory();

    public abstract Class getEmployeeClass();

    public abstract Class getDepartmentClass();
}
