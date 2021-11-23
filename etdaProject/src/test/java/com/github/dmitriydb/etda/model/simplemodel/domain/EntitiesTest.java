package com.github.dmitriydb.etda.model.simplemodel.domain;

import com.github.dmitriydb.etda.dbutils.DbManager;
import org.junit.jupiter.api.Test;

public class EntitiesTest {

    @Test
    public void testHibernateEntities(){
        DbManager.init();
        new EntityTest(Employee.class).testEntity();
        new EntityTest(Salary.class).testEntity();
        new EntityTest(Department.class).testEntity();
        new EntityTest(Title.class).testEntity();
        new EntityTest(DepartmentManager.class).testEntity();
        new EntityTest(DepartmentEmployee.class).testEntity();
        new EntityTest(CurrentDepartmentEmployee.class).testEntity();
    }
}
