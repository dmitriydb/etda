package com.github.dmitriydb.etda.controller.console;

import com.github.dmitriydb.etda.model.EtdaModel;
import com.github.dmitriydb.etda.view.console.ConsoleView;

public class ConsoleController {
    private EtdaModel model;
    private ConsoleView view;

    public void updateView(){
        view.updateSelf();
    }
}
