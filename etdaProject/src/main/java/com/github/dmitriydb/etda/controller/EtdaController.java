package com.github.dmitriydb.etda.controller;

import com.github.dmitriydb.etda.model.EtdaModel;
import com.github.dmitriydb.etda.view.EtdaView;
import com.github.dmitriydb.etda.view.console.ConsoleViewRequest;

import java.io.Serializable;

public abstract class EtdaController {
    protected EtdaModel model;
    protected EtdaView view;

    public void updateView(){
        view.updateSelf();
    }

    public abstract void getScrollableList(Class clazz, ConsoleViewRequest request);

    public abstract void getEntireList(Class clazz, ConsoleViewRequest request);

    public abstract void processUserAction(ConsoleViewRequest request);

    public EtdaModel getModel() {
        return model;
    }

    public EtdaView getView() {
        return view;
    }

    public abstract Object getEntity(Class clazz, Serializable id);

}
