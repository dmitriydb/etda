package com.github.dmitriydb.etda.model.simplemodel.dao;

import com.github.dmitriydb.etda.dbutils.DbManager;
import com.github.dmitriydb.etda.model.simplemodel.domain.Title;
import org.junit.jupiter.api.Test;

import java.util.List;

class TitleDAOTest {

    @Test
    public void testingTitleListReading(){
        DbManager.init();
        SimpleTitleDAO dao = new SimpleTitleDAO();
        List<Title> titles = dao.findAllByEmployeeNumber(10005L);
        System.out.println(titles);
    }

}