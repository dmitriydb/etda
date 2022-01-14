package com.github.dmitriydb.etda.dbutils;

import com.github.dmitriydb.etda.model.dao.SimpleDAO;
import com.github.dmitriydb.etda.model.dao.UserDAO;
import com.github.dmitriydb.etda.model.simplemodel.domain.*;
import com.github.dmitriydb.etda.security.SecurityManager;
import com.github.dmitriydb.etda.security.SecurityRole;
import com.github.dmitriydb.etda.security.User;
import com.github.dmitriydb.etda.service.TestDataGenerator;
import org.apache.catalina.Manager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;

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

        String currentProfile = "";
        if (sessionFactory != null) return;
        Configuration cfg = new Configuration();
        try {
            Properties springProperties = new Properties();
            springProperties.load(DbManager.class.getClassLoader().getResourceAsStream("application.properties"));
            currentProfile = springProperties.getProperty("spring.profiles.active");
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

        //инициализация тестового контента для h2

        if (currentProfile.equals("h2")){
            List<Employee> employees = new ArrayList<>();
            List<Department> departments = new ArrayList<>();
            SimpleDAO edao = new SimpleDAO(Employee.class);
            SimpleDAO sdao = new SimpleDAO(Salary.class);
            SimpleDAO ddao = new SimpleDAO(Department.class);
            SimpleDAO mdao = new SimpleDAO(Manager.class);
            SimpleDAO tdao = new SimpleDAO(Title.class);

            for (int i = 0; i < 15; i++){
                Department d = TestDataGenerator.generateDepartment();
                ddao.create(d);
                departments.add(d);
            }

            for (int i = 0; i < 999; i++){
                Employee e = TestDataGenerator.generateEmployee();
                employees.add(e);
                edao.create(e);

                //назначаем случайную зарплату
                Salary s = new Salary();
                SalaryOrder so = new SalaryOrder();
                so.setFromDate(TestDataGenerator.generateDate());
                so.setEmployeeNumber(e.getEmployeeNumber());
                s.setSalaryOrder(so);
                s.setToDate(TestDataGenerator.generateToDate());
                s.setSalary(ThreadLocalRandom.current().nextLong(1000000));
                sdao.create(s);

                Title t = TestDataGenerator.generateTitle();
                t.getTitleOrder().setEmployeeNumber(e.getEmployeeNumber());
                tdao.create(t);

                //С шансом в 10% назначаем менеджером

                if (ThreadLocalRandom.current().nextInt(100) < 10){
                    DepartmentManager manager = new DepartmentManager();
                    DepartmentEmployeeSuite suite = new DepartmentEmployeeSuite();
                    suite.setDepartmentId(departments.get(ThreadLocalRandom.current().nextInt(departments.size())).getDepartmentId());
                    suite.setEmployeeNumber(e.getEmployeeNumber());
                    manager.setDepartmentManagerSuite(suite);
                    manager.setFromDate(TestDataGenerator.generateDate());
                    manager.setToDate(TestDataGenerator.generateToDate());
                    mdao.create(manager);
                }
            }

        }



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
