package com.github.dmitriydb.etda.controller.console;

import com.github.dmitriydb.etda.controller.EtdaController;
import com.github.dmitriydb.etda.model.EtdaModel;
import com.github.dmitriydb.etda.model.simplemodel.domain.*;
import com.github.dmitriydb.etda.view.console.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.text.View;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConsoleController extends EtdaController {

    private ConsoleViewRequest lastListRequest;

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
        entitiesList.put(ConsoleViewOptions.CREATE_EMPLOYEE, Employee.class);
        entitiesList.put(ConsoleViewOptions.UPDATE_EMPLOYEE, Employee.class);
        entitiesList.put(ConsoleViewOptions.DELETE_EMPLOYEE, Employee.class);

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
                    consoleViewUpdate.addMessage(employee);
                }
                consoleViewUpdate.setTotal(entities.size());
                consoleViewUpdate.setLeftPosition(1 + request.getOffset());
                consoleViewUpdate.setRightPosition(getView().getMaxResults() + request.getOffset());
            } else {
                List<Object> entities = model.findEntitiesFiltered(clazz, request.getFilter(), view.getMaxResults(), request.getOffset());
                for (Object entity : entities) {
                    consoleViewUpdate.addMessage(entity);
                }
                consoleViewUpdate.setTotal(entities.size());
                consoleViewUpdate.setLeftPosition(1 + request.getOffset());
                consoleViewUpdate.setRightPosition(getView().getMaxResults() + request.getOffset());
            }

            view.changeState(ViewState.WAITING_USER_KEY);
            this.lastListRequest = request;
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
                consoleViewUpdate.addMessage(entity);
            }
            consoleViewUpdate.setTotal(entities.size());
            consoleViewUpdate.setLeftPosition(1 + request.getOffset());
            consoleViewUpdate.setRightPosition(entities.size());
            view.changeState(ViewState.WAITING_USER_KEY);
            this.lastListRequest = request;
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
            switch (option.getActionType()) {
                case SCROLLABLE:
                    this.getScrollableList(entitiesList.get(option), request);
                    break;
                case SHOW:
                    this.getEntireList(entitiesList.get(option), request);
                    break;
                case CREATE:
                    this.createEntity(entitiesList.get(option), request.getBean());
                    break;
                case UPDATE:
                    this.updateEntity(entitiesList.get(option), request.getBean());
                    break;
                case DELETE:
                    this.deleteEntity(entitiesList.get(option), request.getId());
                    break;

            }

        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
    }

    private void createEntity(Class clazz, Object bean) {
        try {
            logger.debug("bean = {}", bean);
            model.createEntity(clazz, bean);
            ConsoleViewUpdate consoleViewUpdate = new ConsoleViewUpdate();
            consoleViewUpdate.addMessage("Successfully created");

            /*if (view.getCurrentOption().getActionType().equals(ConsoleActionType.SCROLLABLE)){

                view.changeState(ViewState.WAITING_USER_KEY);
            }*/

            view.changeState(ViewState.MENU);
            ((ConsoleView) this.view).processConsoleViewUpdate(consoleViewUpdate);
        } catch (Exception ex) {
            logger.error("{}", ex);
            view.setCurrentOption(null);
        }
    }

    private void deleteEntity(Class clazz, Serializable id) {
        try {
            logger.debug("id = {}", id);
            model.deleteEntity(clazz, id);
            ConsoleViewUpdate consoleViewUpdate = new ConsoleViewUpdate();
            consoleViewUpdate.addMessage("Successfully deleted");

            /*if (view.getCurrentOption().getActionType().equals(ConsoleActionType.SCROLLABLE)){

                view.changeState(ViewState.WAITING_USER_KEY);
            }*/

            view.changeState(ViewState.MENU);
            ((ConsoleView) this.view).processConsoleViewUpdate(consoleViewUpdate);
        } catch (Exception ex) {
            logger.error("{}", ex);
            view.setCurrentOption(null);
        }
    }

    private void updateEntity(Class clazz, Object bean) {
        try {
            logger.debug("bean = {}", bean);
            model.updateEntity(clazz, bean);
            ConsoleViewUpdate consoleViewUpdate = new ConsoleViewUpdate();
            consoleViewUpdate.addMessage("Successfully updated");
            view.changeState(ViewState.MENU);
            ((ConsoleView) this.view).processConsoleViewUpdate(consoleViewUpdate);
        } catch (Exception ex) {
            logger.error("{}", ex);
            view.setCurrentOption(null);
        }
    }

    @Override
    public Object getEntity(Class clazz, Serializable id) {
        return model.getEntity(clazz, id);
    }
}
