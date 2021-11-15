package com.github.dmitriydb.etda.model.simplemodel.dao;

import com.github.dmitriydb.etda.dbutils.DbManager;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class AbstractDAO {
    protected Session session;
    protected Transaction tx;

    protected void startOperation(){
        session = DbManager.getSession();
        tx = session.beginTransaction();
    }

    protected void endOperation(){
        tx.commit();
        session.close();
    }



}
