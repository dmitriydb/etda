package com.github.dmitriydb.etda.security;

import com.github.dmitriydb.etda.model.dao.SimpleDAO;
import com.github.dmitriydb.etda.view.console.ConsoleActionType;
import com.github.dmitriydb.etda.view.console.ConsoleViewOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import javax.swing.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Класс, который представляет собой роль пользователя в системе
 * Объединяет все доступные пользователю роли во всех видах приложения
 *
 * @version 0.1.1
 * @since 0.1.1
 */
@Entity
public class SecurityRole {

    /**
     * Инициализирует роли пользователей если они еще не существуют в базе
     * Метод должен вызываться в методе DbManager.init() для корректной работы приложения
     *
     * @since 0.1.1
     */
    public static void init(){
        Object role = new SimpleDAO(SecurityRole.class).read((long) EXISTING_ROLES_IDS.ADMIN.ordinal());
        if (role == null) {
            System.out.println("Admin role is empty");
            SecurityRole securityRole = new SecurityRole();
            securityRole.setId(0L);
            for (ConsoleViewOptions c : ConsoleViewOptions.values()) {
                if (c.getActionType() != ConsoleActionType.USER_OPERATION)
                securityRole.grant(c);
            }
            securityRole.grant(ConsoleViewOptions.EXIT);
            new SimpleDAO(SecurityRole.class).create(securityRole);
        }

        Object roleEmployee = new SimpleDAO(SecurityRole.class).read((long) EXISTING_ROLES_IDS.EMPLOYEE.ordinal());
        if (roleEmployee == null)
        {
            SecurityRole securityRole = new SecurityRole();
            securityRole.setId((long) EXISTING_ROLES_IDS.EMPLOYEE.ordinal());
            securityRole.grant(ConsoleViewOptions.EMPLOYEES_LIST);
            securityRole.grant(ConsoleViewOptions.MANAGERS_LIST);
            securityRole.grant(ConsoleViewOptions.TITLES_LIST);
            securityRole.grant(ConsoleViewOptions.DEPARTMENTS_LIST);
            securityRole.grant(ConsoleViewOptions.DEPARTMENT_EMPLOYEES_LIST);
            securityRole.grant(ConsoleViewOptions.DEPARTMENT_EMPLOYEES_LATEST_DATE);
            securityRole.grant(ConsoleViewOptions.EXIT);
            new SimpleDAO(SecurityRole.class).create(securityRole);
        }
    }

    private static final Logger logger = LoggerFactory.getLogger(SecurityRole.class);

    public enum EXISTING_ROLES_IDS {ADMIN, EMPLOYEE};

    @Id
    private Long id;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<ConsoleViewOptions> grantedConsoleOptions = new HashSet<>();

    public static final SecurityRole ADMIN_ROLE() {
        Object role = new SimpleDAO(SecurityRole.class).read((long) EXISTING_ROLES_IDS.ADMIN.ordinal());
        return (SecurityRole) role;
    }

    public static final SecurityRole EMPLOYEE_ROLE() {
        Object role = new SimpleDAO(SecurityRole.class).read((long) EXISTING_ROLES_IDS.EMPLOYEE.ordinal());
        return (SecurityRole) role;
    }


    public SecurityRole grant(ConsoleViewOptions option) {
        grantedConsoleOptions.add(option);
        return this;
    }

    public SecurityRole() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SecurityRole that = (SecurityRole) o;
        return grantedConsoleOptions.equals(that.grantedConsoleOptions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(grantedConsoleOptions);
    }

    public Set<ConsoleViewOptions> getGrantedConsoleOptions() {
        return grantedConsoleOptions;
    }
}
