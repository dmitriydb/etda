package com.github.dmitriydb.etda.controller;

import com.github.dmitriydb.etda.model.EtdaModel;
import com.github.dmitriydb.etda.view.EtdaView;
import com.github.dmitriydb.etda.view.console.ConsoleViewRequest;

public abstract class EtdaController {
    protected EtdaModel model;
    protected EtdaView view;

    public void updateView(){
        view.updateSelf();
    }

    public abstract void getEmployeesList(ConsoleViewRequest request);

    public abstract void getDepartmentList(ConsoleViewRequest request);

    public abstract void processUserAction(ConsoleViewRequest request);

    public EtdaModel getModel() {
        return model;
    }

    public EtdaView getView() {
        return view;
    }
}
