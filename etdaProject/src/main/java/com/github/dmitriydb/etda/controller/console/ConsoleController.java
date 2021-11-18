package com.github.dmitriydb.etda.controller.console;

import com.github.dmitriydb.etda.controller.EtdaController;
import com.github.dmitriydb.etda.model.EtdaModel;
import com.github.dmitriydb.etda.model.simplemodel.domain.*;
import com.github.dmitriydb.etda.view.console.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConsoleController extends EtdaController {

    private final static Logger logger = LoggerFactory.getLogger(ConsoleController.class);

    private static Map<ConsoleViewOptions, Class> entitiesList = new HashMap<>();

    static {
        entitiesList.put(ConsoleViewOptions.EMPLOYEES_LIST, Employee.class);
        entitiesList.put(ConsoleViewOptions.DEPARTMENTS_LIST, Department.class);
        entitiesList.put(ConsoleViewOptions.SALARIES_LIST, Salary.class);
        entitiesList.put(ConsoleViewOptions.TITLES_LIST, Title.class);
        entitiesList.put(ConsoleViewOptions.CURRENT_DEPARTMENT_EMPLOYEES_LIST, CurrentDepartmentEmployee.class);
        entitiesList.put(ConsoleViewOptions.DEPARTMENT_EMPLOYEES_LIST, DepartmentEmployee.class);
        entitiesList.put(ConsoleViewOptions.MANAGERS_LIST, DepartmentManager.class);
        entitiesList.put(ConsoleViewOptions.DEPARTMENT_EMPLOYEES_LATEST_DATE, DepartmentEmployeeLatestDate.class);
    }

    public ConsoleController() {
        model = EtdaModel.getSimpleModel();
        view = new SimpleConsoleView();
        view.setController(this);
        view.updateSelf();
    }

    @Override
    public void getScrollableList(Class clazz, ConsoleViewRequest request) {
        try {
            ConsoleViewUpdate consoleViewUpdate = new ConsoleViewUpdate();
            if (request.getFilter() == null || request.getFilter().equals("")) {
                List<Object> entities = model.findEntities(clazz, view.getMaxResults(), request.getOffset());
                for (Object employee : entities) {
                    consoleViewUpdate.addMessage(employee.toString());
                }
                consoleViewUpdate.setTotal(entities.size());
                consoleViewUpdate.setLeftPosition(1 + request.getOffset());
                consoleViewUpdate.setRightPosition(getView().getMaxResults() + request.getOffset());
            } else {
                List<Object> entities = model.findEntitiesFiltered(clazz, request.getFilter(), view.getMaxResults(), request.getOffset());
                for (Object entity : entities) {
                    consoleViewUpdate.addMessage(entity.toString());
                }
                consoleViewUpdate.setTotal(entities.size());
                consoleViewUpdate.setLeftPosition(1 + request.getOffset());
                consoleViewUpdate.setRightPosition(getView().getMaxResults() + request.getOffset());
            }

            view.changeState(ViewState.WAITING_USER_KEY);
            ((ConsoleView) this.view).processConsoleViewUpdate(consoleViewUpdate);
        } catch (Exception ex) {
            logger.error("{}", ex);
            view.setCurrentOption(null);
        }
    }

    @Override
    public void getEntireList(Class clazz, ConsoleViewRequest request) {
        try {
            ConsoleViewUpdate consoleViewUpdate = new ConsoleViewUpdate();
            List<Object> entities = model.findEntities(clazz);
            for (Object entity : entities) {
                consoleViewUpdate.addMessage(entity.toString());
            }
            consoleViewUpdate.setTotal(entities.size());
            consoleViewUpdate.setLeftPosition(1 + request.getOffset());
            consoleViewUpdate.setRightPosition(entities.size());
            view.changeState(ViewState.WAITING_USER_KEY);
            ((ConsoleView) this.view).processConsoleViewUpdate(consoleViewUpdate);
        } catch (Exception ex) {
            logger.error("{}", ex);
            view.setCurrentOption(null);
        }
    }


    @Override
    public void processUserAction(ConsoleViewRequest request) {
        logger.debug("Processing request {}", request);
        try {
            Integer optionNumber = Integer.valueOf(request.getRequestMessage().trim());
            ConsoleViewOptions option = ConsoleViewOptions.values()[optionNumber - 1];
            view.setCurrentOption(option);
            if (option.getActionType().equals(ConsoleActionType.SCROLLABLE))
                this.getScrollableList(entitiesList.get(option), request);
            else
                this.getEntireList(entitiesList.get(option), request);


        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
    }
}
