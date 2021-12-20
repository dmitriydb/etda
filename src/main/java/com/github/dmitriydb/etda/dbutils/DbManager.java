package com.github.dmitriydb.etda.dbutils;

import com.github.dmitriydb.etda.model.dao.UserDAO;
import com.github.dmitriydb.etda.model.simplemodel.domain.*;
import com.github.dmitriydb.etda.security.SecurityManager;
import com.github.dmitriydb.etda.security.SecurityRole;
import com.github.dmitriydb.etda.security.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Properties;

/**
 * Вспомогательный класс для работы с БД
 * На текущий момент используется, чтобы инициализировать Hibernate и создать фабрику сессий
 * В дальнейшем раздает новые сессии по запросу
 *
 * @version 0.2
 * @since 0.1
 */

@Component
public class DbManager {

    private static final Logger logger = LoggerFactory.getLogger(DbManager.class);

    private static SessionFactory sessionFactory;

    public synchronized static void init() {

        if (sessionFactory != null) return;
        Configuration cfg = new Configuration();
        try {
            Properties springProperties = new Properties();
            springProperties.load(DbManager.class.getClassLoader().getResourceAsStream("application.properties"));
            String currentProfile = springProperties.getProperty("spring.profiles.active");
            Properties hibernateProperties = new Properties();
            hibernateProperties.load(DbManager.class.getClassLoader().getResourceAsStream("hibernate-" + currentProfile + ".properties"));
            cfg.addProperties(hibernateProperties);
        } catch (IOException e) {
            e.printStackTrace();
        }

        cfg.addAnnotatedClass(Employee.class);
        cfg.addAnnotatedClass(Salary.class);
        cfg.addAnnotatedClass(Department.class);
        cfg.addAnnotatedClass(Title.class);
        cfg.addAnnotatedClass(DepartmentManager.class);
        cfg.addAnnotatedClass(DepartmentEmployee.class);
        cfg.addAnnotatedClass(CurrentDepartmentEmployee.class);
        cfg.addAnnotatedClass(DepartmentEmployeeLatestDate.class);
        cfg.addAnnotatedClass(User.class);
        cfg.addAnnotatedClass(SecurityRole.class);
        sessionFactory = cfg.buildSessionFactory();
        SecurityRole.init();

        //инициализация тестовых пользователей

        createTestUser("admin", SecurityRole.ADMIN_ROLE());
        createTestUser("manager", SecurityRole.MANAGER_ROLE());
        createTestUser("employee", SecurityRole.EMPLOYEE_ROLE());
        createTestUser("hr", SecurityRole.HR_ROLE());

    }

    private static void createTestUser(String username, SecurityRole role){
        if (!new UserDAO().isUserExists(username)){
            logger.info("Creating test user " + username);
            User u = new User();
            u.setSecurityRole(role);
            u.setName(username);
            u.setPassword(SecurityManager.hashPassword(username));
            new UserDAO().createUser(u);
        }
    }


    public void initSelf(){
        DbManager.init();
    }

    public Session getNewSession(){
        return sessionFactory.openSession();
    }

    public static Session getSession(){
        return sessionFactory.openSession();
    }
}
