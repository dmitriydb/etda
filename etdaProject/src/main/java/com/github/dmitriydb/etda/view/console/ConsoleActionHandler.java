package com.github.dmitriydb.etda.view.console;

import com.github.dmitriydb.etda.controller.EtdaController;

/**
 * Класс, инкапсулирующий действие пользователя в консольном приложении
 * Необходимо реализовать метод processAction для конкретного действия
 */
public abstract class ConsoleActionHandler{

    private EtdaController controller;

    public ConsoleActionHandler(EtdaController controller){
        this.controller = controller;
    }

    public EtdaController getController() {
        return controller;
    }

    public abstract void processAction(ConsoleViewRequest request);
}
