package com.github.dmitriydb.etda.view.console;

import com.github.dmitriydb.etda.controller.EtdaController;

public class ShowEmployeesListActionHandler extends ConsoleActionHandler{


    public ShowEmployeesListActionHandler(EtdaController controller) {
        super(controller);
    }

    @Override
    public void processAction(ConsoleViewRequest request) {
        this.getController().getEmployeesList(request);
    }
}
