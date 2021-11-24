package com.github.dmitriydb.etda.security;

import com.github.dmitriydb.etda.dbutils.DbManager;
import com.github.dmitriydb.etda.model.dao.SimpleDAO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    public void creatingUserOkay(){
        DbManager.init();
        User u = new User();
        u.setName("test2");
        u.setPassword("123");
        u.setEmail("123");
        u.setSecurityRole(SecurityRole.EMPLOYEE_ROLE());
        new SimpleDAO(User.class).create(u);
    }

}