package com.github.dmitriydb.etda.view.console;

import com.github.dmitriydb.etda.controller.console.ConsoleController;
import com.github.dmitriydb.etda.controller.console.ViewState;
import com.github.dmitriydb.etda.model.EtdaModel;

import java.util.Locale;

public abstract class ConsoleView {

    private Locale locale = Locale.getDefault();
    private ViewState currentState;
    private ConsoleController controller;

    public ConsoleView(){
        currentState = ViewState.CREATED;
    }

    public abstract void updateSelf();

    public abstract void render();

    public void changeLocale(Locale locale){
        this.locale = locale;
    }
}
