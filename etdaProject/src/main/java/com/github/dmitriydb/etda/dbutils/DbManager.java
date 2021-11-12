package com.github.dmitriydb.etda.dbutils;

import com.github.dmitriydb.etda.model.simplemodel.domain.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DbManager {

    private final static SessionFactory sessionFactory;

    static {
        Configuration cfg = new Configuration();
        cfg.addAnnotatedClass(Employee.class);
        cfg.addAnnotatedClass(Salary.class);
        cfg.addAnnotatedClass(Department.class);
        cfg.addAnnotatedClass(Title.class);
        cfg.addAnnotatedClass(DepartmentManager.class);
        cfg.addAnnotatedClass(DepartmentEmployee.class);
        cfg.addAnnotatedClass(CurrentDepartmentEmployee.class);
        sessionFactory = cfg.buildSessionFactory();
    }

    public static Session getSession(){
        return sessionFactory.openSession();
    }
}
