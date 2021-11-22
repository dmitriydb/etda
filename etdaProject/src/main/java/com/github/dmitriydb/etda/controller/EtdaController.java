package com.github.dmitriydb.etda.controller;

import com.github.dmitriydb.etda.model.EtdaModel;
import com.github.dmitriydb.etda.view.EtdaView;
import com.github.dmitriydb.etda.view.console.ConsoleViewRequest;

import java.io.Serializable;

/**
 * Абстрактный класс для контроллера
 * @version 0.1
 * @since 0.1
 */
public abstract class EtdaController {
    protected EtdaModel model;
    protected EtdaView view;

    /**
     * Вызывается, когда контроллер обновляет представление
     */
    public void updateView(){
        view.updateSelf();
    }

    /**
     * Метод вызывается представлением когда нужно обработать действие пользователя
     * @param request инкапсулированный запрос пользователя
     */
    public abstract void processUserAction(ConsoleViewRequest request);

    public EtdaModel getModel() {
        return model;
    }

    public EtdaView getView() {
        return view;
    }

    /**
     * Метод получает из модели отдельную сущность по ID
     * @param clazz класс сущности
     * @param id
     * @return
     */
    public abstract Object getEntity(Class clazz, Serializable id);

}
