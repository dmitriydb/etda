package com.github.dmitriydb.etda.model.simplemodel.domain;

/**
 * Класс, который содержит объект типа DepartmentManager плюс имя + фамилию менеджера для демонстрации на странице менеджеров
 * @version 0.3
 * @since 0.3
 */
public class DepartmentManagerDTO extends DepartmentManager{
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
