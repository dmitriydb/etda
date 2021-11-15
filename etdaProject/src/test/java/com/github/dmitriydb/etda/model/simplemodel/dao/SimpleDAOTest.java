package com.github.dmitriydb.etda.model.simplemodel.dao;

import com.github.dmitriydb.etda.model.simplemodel.domain.Employee;
import com.github.dmitriydb.etda.model.simplemodel.domain.Title;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimpleDAOTest {

    @Test
    public void readATitle(){
        SimpleDAO dao = new SimpleDAO(Employee.class);
        Employee t = (Employee)dao.read(10001L);
        System.out.println(t);
    }

}