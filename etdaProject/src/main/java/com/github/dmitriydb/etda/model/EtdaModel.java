package com.github.dmitriydb.etda.model;

import com.github.dmitriydb.etda.model.simplemodel.domain.Department;
import com.github.dmitriydb.etda.model.simplemodel.domain.Employee;
import com.github.dmitriydb.etda.model.simplemodel.domain.Salary;

import javax.management.ObjectName;
import java.io.Serializable;
import java.util.List;

/**
 * Абстрактная модель данных
 * @version 0.1
 * @since 0.1
 */
public abstract class EtdaModel {
    public static EtdaModel getSimpleModel(){
        return new SimpleModel();
    }

    /**
     * Возвращает пустой список, если результаты не найдены
     */
    public abstract List<Object> findEntities(Class clazz, int maxResults, int offset);

    /**
     * Возвращает пустой список, если результаты не найдены
     */
    public abstract List<Object> findEntitiesFiltered(Class clazz, String filter, int maxResults, int offset);

    public abstract List<Object> findEntities(Class clazz);

    /**
     * Возвращает пустой список, если результаты не найдены
     */
    public abstract List<Object> findList(Class clazz, int maxResults, int offset);

    /**
     * Создает сущность, либо генерирует исключение IllegalArgumentException
     */
    public abstract void createEntity(Class clazz, Object entity);

    /**
     * Обновляет объект, либо генерирует исключение IllegalArgumentException (например, если объекта с таким ID не существует)
     * */
    public abstract boolean updateEntity(Class clazz, Object entity);

    /**
     * Удаляет объект, либо генерирует исключение IllegalArgumentException (например, если объекта с таким ID не существует)
     */
    public abstract void deleteEntity(Class clazz, Serializable id) throws IllegalArgumentException;

    /**
     * Возвращает null, если объект не найден
     * @param clazz класс искомой сущности
     * @param id id сущности
     * @return результат поиска (1 объект)
     */
    public abstract Object getEntity(Class clazz, Serializable id);

}
