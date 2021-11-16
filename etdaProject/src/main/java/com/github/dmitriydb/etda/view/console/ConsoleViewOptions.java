package com.github.dmitriydb.etda.view.console;

public enum ConsoleViewOptions {
    EMPLOYEES_LIST("EmployeesList"), DEPARTMENT_LIST("DepartmentsList"),
    CREATE_EMPLOYEE("CreateEmployee"), UPDATE_EMPLOYEE("UpdateEmployee"), DELETE_EMPLOYEE("DeleteEmployee");

    private String optionLabel;

    ConsoleViewOptions(String optionLabel){
        this.optionLabel = optionLabel;
    }

    public String getOptionLabel() {
        return optionLabel;
    }

}
