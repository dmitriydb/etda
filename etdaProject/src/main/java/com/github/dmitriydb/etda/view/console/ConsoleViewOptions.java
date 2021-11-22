package com.github.dmitriydb.etda.view.console;

import com.github.dmitriydb.etda.model.simplemodel.domain.*;

/**
 * Класс, который содержит возможные действия пользователя в консольном интерфейсе
 *
 * Возможно стоит перенести на более высокий уровень и распространить на веб-приложение
 * @version 0.1
 * @since 0.1
 */
public enum ConsoleViewOptions {

    //List operations
    EMPLOYEES_LIST("EmployeesList", ConsoleActionType.SCROLLABLE, Employee.class),
    DEPARTMENTS_LIST("DepartmentsList", ConsoleActionType.SHOW, Department.class),
    TITLES_LIST("TitlesList", ConsoleActionType.SCROLLABLE, Title.class),
    SALARIES_LIST("SalariesList",  ConsoleActionType.SCROLLABLE, Salary.class),
    MANAGERS_LIST("ManagersList",  ConsoleActionType.SCROLLABLE, DepartmentManager.class),
    DEPARTMENT_EMPLOYEES_LIST("DeptEmpList",  ConsoleActionType.SCROLLABLE, DepartmentEmployee.class),
    CURRENT_DEPARTMENT_EMPLOYEES_LIST("CurrentDeptEmpList",  ConsoleActionType.SCROLLABLE, CurrentDepartmentEmployee.class),
    DEPARTMENT_EMPLOYEES_LATEST_DATE("DeptEmpLatestDate", ConsoleActionType.SCROLLABLE, DepartmentEmployeeLatestDate.class),
    CREATE_EMPLOYEE("CreateEmployee", ConsoleActionType.CREATE, Employee.class),
    UPDATE_EMPLOYEE("UpdateEmployee", ConsoleActionType.UPDATE, Employee.class),
    DELETE_EMPLOYEE("DeleteEmployee", ConsoleActionType.DELETE, Employee.class),
    CREATE_DEPARTMENT("CreateDepartment", ConsoleActionType.CREATE, Department.class),
    UPDATE_DEPARTMENT("UpdateDepartment", ConsoleActionType.UPDATE, Department.class),
    DELETE_DEPARTMENT("DeleteDepartment", ConsoleActionType.DELETE, Department.class),
    CREATE_TITLE("CreateTitle", ConsoleActionType.CREATE, Title.class),
    DELETE_TITLE("DeleteTitle", ConsoleActionType.DELETE, Title.class),
    CREATE_SALARY("CreateSalary", ConsoleActionType.CREATE, Salary.class),
    DELETE_SALARY("DeleteSalary", ConsoleActionType.DELETE, Salary.class),
    CREATE_DEPT_EMP("CreateDeptEmp", ConsoleActionType.CREATE, DepartmentEmployee.class),
    DELETE_DEPT_EMP("DeleteDeptEmp", ConsoleActionType.DELETE, DepartmentEmployee.class),
    CREATE_DEPT_MANAGER("CreateDeptManager", ConsoleActionType.CREATE, DepartmentManager.class),
    DELETE_DEPT_MANAGER("DeleteDeptManager", ConsoleActionType.DELETE, DepartmentManager.class);

    /**
     * Название действия
     * Совпадает с именем ресурса в комплекте ресурсов
     */
    private String optionLabel;
    /**
     * Тип действия
     * @see {@link com.github.dmitriydb.etda.view.console.ConsoleActionType}
     */
    private ConsoleActionType actionType;

    /**
     * Класс сущности, над которой совершается данное действие
     */
    private Class entityClass;

    ConsoleViewOptions(String optionLabel, ConsoleActionType actionType, Class clazz){
        this.optionLabel = optionLabel;
        this.actionType = actionType;
        this.entityClass = clazz;
    }

    public String getOptionLabel() {
        return optionLabel;
    }

    public ConsoleActionType getActionType() {
        return actionType;
    }

    public Class getEntityClass() {
        return entityClass;
    }
}
