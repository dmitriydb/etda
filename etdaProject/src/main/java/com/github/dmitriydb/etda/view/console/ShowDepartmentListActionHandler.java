package com.github.dmitriydb.etda.view.console;

import com.github.dmitriydb.etda.controller.EtdaController;

public class ShowDepartmentListActionHandler extends ConsoleActionHandler{
    public ShowDepartmentListActionHandler(EtdaController controller) {
        super(controller);
    }

    @Override
    public void processAction(ConsoleViewRequest request) {

        this.getController().getDepartmentList(request);
    }
}
