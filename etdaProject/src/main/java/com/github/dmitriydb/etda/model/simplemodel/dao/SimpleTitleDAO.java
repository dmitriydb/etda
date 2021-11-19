package com.github.dmitriydb.etda.model.simplemodel.dao;

import com.github.dmitriydb.etda.model.dao.SimpleDAO;
import com.github.dmitriydb.etda.model.simplemodel.domain.Title;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Query;
import java.util.List;

/**
 * DAO класс для сущности Title, который позволяет искать должности сотрудников по номеру сотрудника
 * Стандартный вариант {@link com.github.dmitriydb.etda.model.dao.SimpleDAO} не подходит для этой операции, т.к
 * первичным ключом сущности Title является embeddable класс TitleOrder, а не поле employeeNumber
 *
 * @version 0.1
 * @since 0.1
 */
public class SimpleTitleDAO extends SimpleDAO {

    private static final Logger logger = LoggerFactory.getLogger(SimpleTitleDAO.class);

    public SimpleTitleDAO(){
        super(Title.class);
    }

    /**
     * поиск должности по номеру сотрудника
     * @param id номер сотрудника
     * @return
     * @since 0.1
     */
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
