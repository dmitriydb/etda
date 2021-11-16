package com.github.dmitriydb.etda.model.simplemodel.dao;

import com.github.dmitriydb.etda.model.dao.SimpleDAO;
import com.github.dmitriydb.etda.model.simplemodel.domain.Employee;
import org.junit.jupiter.api.Test;

class SimpleDAOTest {

    @Test
    public void readATitle(){
        SimpleDAO dao = new SimpleDAO(Employee.class);
        Employee t = (Employee)dao.read(10001L);
        System.out.println(t);
    }

}