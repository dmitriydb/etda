package com.github.dmitriydb.etda.model.simplemodel.dao;

import com.github.dmitriydb.etda.model.simplemodel.domain.Title;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TitleDAOTest {

    @Test
    public void testingTitleListReading(){
        TitleDAO dao = new TitleDAO();
        List<Title> titles = dao.findAllByEmployeeNumber(10005L);
        System.out.println(titles);
    }

}