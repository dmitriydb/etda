package com.github.dmitriydb.etda.model.simplemodel.dao;

import com.github.dmitriydb.etda.model.simplemodel.domain.Title;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Query;
import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

public class TitleDAO extends SimpleDAO{

    private static final Logger logger = LoggerFactory.getLogger(TitleDAO.class);

    public TitleDAO(){
        super(Title.class);
    }

    public List<Title> findAllByEmployeeNumber(Long id) {
        startOperation();
        try{
           Query query = session.createQuery("FROM Title WHERE titleOrder.employeeNumber = :number");
           query.setParameter("number", id);
           List<Title> result = query.getResultList();
           return result;
        }
        catch (Exception ex){
            logger.error("Error occurred while reading titles list for employee number {}: {}", id, ex.getMessage());
            return null;
        }
        finally {
            endOperation();
        }

    }
}
