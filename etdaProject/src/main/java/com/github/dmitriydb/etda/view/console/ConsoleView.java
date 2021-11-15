package com.github.dmitriydb.etda.view.console;

import com.github.dmitriydb.etda.controller.console.ConsoleController;
import com.github.dmitriydb.etda.controller.console.ViewState;
import com.github.dmitriydb.etda.model.EtdaModel;

public abstract class ConsoleView {

    private ViewState currentState;
    private ConsoleController controller;

    public ConsoleView(){
        currentState = ViewState.CREATED;
    }

    public abstract void updateSelf();

    public abstract void render();
}
