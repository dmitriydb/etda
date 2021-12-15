package com.github.dmitriydb.etda.model.dao;

import com.github.dmitriydb.etda.dbutils.DbManager;
import com.github.dmitriydb.etda.model.simplemodel.domain.DepartmentEmployee;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DepartmentEmployeeDAOTest {


   static Session testsession = mock(Session.class);
   static ArgumentCaptor<DepartmentEmployeeDAO> argumentCaptor = ArgumentCaptor.forClass(DepartmentEmployeeDAO.class);

    public static class DepartmentEmployeeTestDAO extends DepartmentEmployeeDAO{
                @Override
                public void startOperation(){
                    Query query = mock(Query.class);
                    DepartmentEmployee e = new DepartmentEmployee();
                    List<DepartmentEmployee> list = new ArrayList<DepartmentEmployee>();
                    list.add(e);
                    when(query.getResultList()).thenReturn(list);
                    when(testsession.createQuery((String) any())).thenReturn(query);

                    this.session = testsession;

                }

                @Override
                public void endOperation(){
                }
    }


    @Test
    public void daoIsDeletingEntity(){
       DepartmentEmployeeTestDAO dao = new DepartmentEmployeeTestDAO();
        dao.deleteDepartmentEmployeeByEmpNumber(1L);
       verify(testsession).delete(any());
    }

}