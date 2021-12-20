package com.github.dmitriydb.etda.model.dao;

import com.github.dmitriydb.etda.dbutils.DbManager;
import com.github.dmitriydb.etda.model.simplemodel.domain.DepartmentEmployee;
import com.github.dmitriydb.etda.model.simplemodel.domain.Employee;
import junitparams.JUnitParamsRunner;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(JUnitParamsRunner.class)
public class SimpleDAOTest {

    static {
        DbManager.init();
    }

    @Test
    public void creatingAndDeletingSomeEntities(){
        Employee e = new Employee();
        e.setBirth_date(Date.valueOf("1111-11-11"));
        e.setHireDate((Date.valueOf("1111-11-11")));
        e.setGender('M');
        e.setLastName("abbbbaaa");
        e.setFirstName("bbbaaabbb");
        SimpleDAO simpleDAO = new SimpleDAO(Employee.class);

        simpleDAO.create(e);

        Employee e2 = (Employee) simpleDAO.read(e.getEmployeeNumber());

        assertThat(e).isEqualTo(e2);

        simpleDAO.delete(e.getEmployeeNumber());

        Employee e3 = (Employee) simpleDAO.read(e.getEmployeeNumber());

        assertThat(e3 == null);

    }

}