package com.github.dmitriydb.etda.view.console;

public enum ConsoleViewOptions {

    //List operations
    EMPLOYEES_LIST("EmployeesList", ConsoleActionType.SCROLLABLE),
    DEPARTMENTS_LIST("DepartmentsList"),
    TITLES_LIST("TitlesList", ConsoleActionType.SCROLLABLE),
    SALARIES_LIST("SalariesList",  ConsoleActionType.SCROLLABLE),
    MANAGERS_LIST("ManagersList",  ConsoleActionType.SCROLLABLE),
    DEPARTMENT_EMPLOYEES_LIST("DeptEmpList",  ConsoleActionType.SCROLLABLE),
    CURRENT_DEPARTMENT_EMPLOYEES_LIST("CurrentDeptEmpList",  ConsoleActionType.SCROLLABLE),
    DEPARTMENT_EMPLOYEES_LATEST_DATE("DeptEmpLatestDate", ConsoleActionType.SCROLLABLE),

    //Create operations
    CREATE_EMPLOYEE("CreateEmployee", ConsoleActionType.MODIFYING),

    //Update operations
    UPDATE_EMPLOYEE("UpdateEmployee", ConsoleActionType.MODIFYING),

    //Delete operations
    DELETE_EMPLOYEE("DeleteEmployee", ConsoleActionType.MODIFYING);

    private String optionLabel;
    private ConsoleActionType actionType;

    ConsoleViewOptions(String optionLabel, ConsoleActionType actionType){
        this.optionLabel = optionLabel;
        this.actionType = actionType;
    }

    ConsoleViewOptions(String optionLabel){
        this.optionLabel = optionLabel;
        this.actionType = ConsoleActionType.SHOW;
    }

    public String getOptionLabel() {
        return optionLabel;
    }

    public ConsoleActionType getActionType() {
        return actionType;
    }
}
