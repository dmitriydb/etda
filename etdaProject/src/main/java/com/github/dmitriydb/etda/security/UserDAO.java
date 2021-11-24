package com.github.dmitriydb.etda.security;

import com.github.dmitriydb.etda.model.dao.AbstractDAO;
import org.hibernate.query.Query;

import java.util.List;

/**
 * DAO для класса User
 *
 * @since 0.1.1
 */
public class UserDAO extends AbstractDAO {


    /**
     * Ищет пользователя по логину и возвращает true, если он существует в базе
     * @param name
     *
     * @since 0.1.1
     */
    public boolean isUserExists(String name){
        startOperation();
        Query query = session.createQuery("FROM User WHERE name = :name");
        query.setParameter("name", name);
        List<User> result = query.getResultList();
        return result.size() > 0;
    }

    /**
     * Ищет пользователя по имени и возвращает объект класса User
     *
     * Если объект не найден, то возвращает null
     * @param name
     * @return
     */
    public User getUserByName(String name){
        if (!isUserExists(name)) return null;
        Query query = session.createQuery("FROM User WHERE name = :name");
        query.setParameter("name", name);
        List<User> result = query.getResultList();
        return result.get(0);
    }
}
