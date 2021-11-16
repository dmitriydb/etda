package com.github.dmitriydb.etda.controller.console;

import com.github.dmitriydb.etda.controller.EtdaController;
import com.github.dmitriydb.etda.model.SimpleModel;
import com.github.dmitriydb.etda.model.dao.SimpleDAO;
import com.github.dmitriydb.etda.model.simplemodel.domain.Employee;
import com.github.dmitriydb.etda.view.console.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConsoleController extends EtdaController {

    private final static Logger logger = LoggerFactory.getLogger(ConsoleController.class);

    private Map<Integer, ConsoleActionHandler> actionProcessorMap = new HashMap<>();

    /*
    Инициализация обработчиков действий
     */
    {
        actionProcessorMap.put(ConsoleViewOptions.EMPLOYEES_LIST.ordinal() + 1, new ShowEmployeesListActionHandler(this));
        actionProcessorMap.put(ConsoleViewOptions.DEPARTMENT_LIST.ordinal() + 1, new ShowDepartmentListActionHandler(this));
    }

    public ConsoleController(){
        model = new SimpleModel();
        view = new SimpleConsoleView();
        view.setController(this);
        view.updateSelf();
    }

    @Override
    public void getEmployeesList(ConsoleViewRequest request) {
        ConsoleViewUpdate consoleViewUpdate = new ConsoleViewUpdate();
        SimpleDAO dao = model.getDaoFactory().getSimpleDAO(model.getEmployeeClass());
        if (request.getFilter() == null || request.getFilter().equals("")) {
            for (Object employee : dao.readList(view.getMaxResults(), request.getOffset())){
                consoleViewUpdate.addMessage(employee.toString());
            }
        }
        else
        {
            List<Employee> employees = model.getDaoFactory().getEmployeeDAO().findAllByFilter(request.getFilter(), view.getMaxResults(), request.getOffset());
            for (Employee employee : employees){
                consoleViewUpdate.addMessage(employee.toString());
            }
        }
        view.changeState(ViewState.WAITING_USER_KEY);
        view.setCurrentOption(ConsoleViewOptions.EMPLOYEES_LIST);
        ((ConsoleView) this.view).processConsoleViewUpdate(consoleViewUpdate);
    }

    @Override
    public void getDepartmentList(ConsoleViewRequest request) {

    }

    @Override
    public void processUserAction(ConsoleViewRequest request){
        logger.debug("Processing request {}", request);
        try{
            Integer optionNumber = Integer.valueOf(request.getRequestMessage().trim());
            actionProcessorMap.get(optionNumber).processAction(request);
        }
        catch (Exception ex){
            logger.error(ex.getMessage());
        }
    }
}
