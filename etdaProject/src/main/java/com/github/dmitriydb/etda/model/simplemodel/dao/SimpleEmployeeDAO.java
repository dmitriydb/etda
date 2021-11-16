package com.github.dmitriydb.etda.model.simplemodel.dao;

import com.github.dmitriydb.etda.model.dao.SimpleDAO;
import com.github.dmitriydb.etda.model.simplemodel.domain.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Query;
import java.util.List;

public class SimpleEmployeeDAO extends SimpleDAO {

    private static final Logger logger = LoggerFactory.getLogger(SimpleEmployeeDAO.class);

    public SimpleEmployeeDAO(){
        super(Employee.class);
    }

    public List<Employee> findAllByFilter(String filter, int howMuch, int offset) {
        startOperation();
        try{
            logger.debug("filter = {}", filter);
            Query query = session.createQuery("FROM Employee WHERE lastName LIKE :f OR firstName LIKE :f");
            query.setParameter("f", "%" + filter + "%");
            query.setMaxResults(howMuch);
            query.setFirstResult(offset);
            List<Employee> result = query.getResultList();
            System.out.println(result.size());
            return result;
        }
        catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
        finally {
            endOperation();
        }
    }
}
