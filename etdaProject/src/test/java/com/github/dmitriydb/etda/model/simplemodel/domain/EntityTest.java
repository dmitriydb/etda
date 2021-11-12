package com.github.dmitriydb.etda.model.simplemodel.domain;

import com.github.dmitriydb.etda.dbutils.DbManager;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Query;

import java.util.List;

class EntityTest {

    private Class testingEntityClass;

    public EntityTest(Class clazz){
        testingEntityClass = clazz;
    }

    private static final Logger logger = LoggerFactory.getLogger(EntityTest.class);


    public void testEntity(){
        Session session = DbManager.getSession();
        Transaction tx = session.beginTransaction();

        Query query = session.createQuery("FROM " + testingEntityClass.getSimpleName());
        query.setMaxResults(1);
        List<Object> result = query.getResultList();
        if (result.size() != 0){
            logger.info("some entity loaded = {}", result.get(0));
        }

        tx.commit();
        session.close();
    }

}