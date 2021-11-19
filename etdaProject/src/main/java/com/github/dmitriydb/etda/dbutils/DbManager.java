package com.github.dmitriydb.etda.dbutils;

import com.github.dmitriydb.etda.model.simplemodel.domain.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Вспомогательный класс для работы с БД
 * На текущий момент используется, чтобы инициализировать Hibernate и создать фабрику сессий
 * В дальнейшем раздает новые сессии по запросу
 *
 * @version 0.1
 * @since 0.1
 */
public class DbManager {

    private static SessionFactory sessionFactory;

    public static void init() {
        Configuration cfg = new Configuration();
        cfg.addAnnotatedClass(Employee.class);
        cfg.addAnnotatedClass(Salary.class);
        cfg.addAnnotatedClass(Department.class);
        cfg.addAnnotatedClass(Title.class);
        cfg.addAnnotatedClass(DepartmentManager.class);
        cfg.addAnnotatedClass(DepartmentEmployee.class);
        cfg.addAnnotatedClass(CurrentDepartmentEmployee.class);
        cfg.addAnnotatedClass(DepartmentEmployeeLatestDate.class);
        sessionFactory = cfg.buildSessionFactory();
    }

    public static Session getSession(){
        return sessionFactory.openSession();
    }
}
