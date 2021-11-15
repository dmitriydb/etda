package com.github.dmitriydb.etda.model;

public interface EtdaDao<TYPE, ID> {
    boolean create (TYPE entity);
}
