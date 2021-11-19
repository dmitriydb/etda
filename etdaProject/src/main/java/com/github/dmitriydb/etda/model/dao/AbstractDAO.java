package com.github.dmitriydb.etda.model.dao;

import com.github.dmitriydb.etda.dbutils.DbManager;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * Класс, объединяющий в себе стандартные операции для всех возможных DAO
 * такие как получение сессии, открытие-закрытие транзакции, закрытие сессии и т.д
 * другие DAO классы должны наследовать данный класс, чтобы применять эти методы в операциях
 *
 * @version 0.1
 * @since 0.1
 */
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
