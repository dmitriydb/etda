package com.github.dmitriydb.etda.controller.console;

import com.github.dmitriydb.etda.controller.EtdaController;
import com.github.dmitriydb.etda.model.EtdaModel;
import com.github.dmitriydb.etda.model.simplemodel.domain.*;
import com.github.dmitriydb.etda.resources.ResourceBundleManager;
import com.github.dmitriydb.etda.view.console.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.text.View;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Контроллер для консольного представления
 * Возможно, в будущем будет преобразован в общий контроллер и для веб-приложения
 *
 * @version 0.1
 * @since 0.1
 */
public class ConsoleController extends EtdaController {

    /**
     * последний запрос, обработанный контроллером из представления
     */
    private ConsoleViewRequest lastListRequest;

    private final static Logger logger = LoggerFactory.getLogger(ConsoleController.class);

    /**
     * В данный момент по умолчанию контроллер работает с простым консольным представлением
     *
     * @since 0.1
     */
    public ConsoleController() {
        model = EtdaModel.getSimpleModel();
        view = new SimpleConsoleView();
        view.setController(this);
    }

    public void startWork(){
        view.updateSelf();
    }

    /**
     * Возвращает список сущностей на основе параметров запроса
     * Предполагается, что в запросе request установлены следующие параметры для запроса:
     * offset - смещение относительно полной выборки
     * filter - строка фильтра для выборки
     * <p>
     * После обработки запроса метод формирует объект ConsoleViewUpdate и передает в метод обработки событий представления
     *
     * @param clazz   класс сущности
     * @param request объект запроса
     * @since 0.1
     */
    private void getScrollableList(Class clazz, ConsoleViewRequest request) {
        try {
            ConsoleViewUpdate consoleViewUpdate = new ConsoleViewUpdate();
            if (request.getFilter() == null || request.getFilter().equals("")) {
                List<Object> entities = model.findEntities(clazz, view.getMaxResults(), request.getOffset());
                for (Object employee : entities) {
                    consoleViewUpdate.addMessage(employee);
                }
            } else {
                List<Object> entities = model.findEntitiesFiltered(clazz, request.getFilter(), view.getMaxResults(), request.getOffset());
                for (Object entity : entities) {
                    consoleViewUpdate.addMessage(entity);
                }
            }

            view.changeState(ViewState.WAITING_USER_KEY);
            this.lastListRequest = request;
            ((ConsoleView) this.view).processConsoleViewUpdate(consoleViewUpdate);
        } catch (Exception ex) {
            logger.error("{}", ex);
            view.setCurrentOption(null);
        }
    }

    /**
     * Возвращает полный список сущностей
     * Параметры запроса (offset, filter) не учитываются
     * <p>
     * После обработки запроса метод формирует объект ConsoleViewUpdate и передает в метод обработки событий представления
     *
     * @param clazz   класс сущности
     * @param request объект запроса
     * @since 0.1
     */
    private void getEntireList(Class clazz, ConsoleViewRequest request) {
        try {
            ConsoleViewUpdate consoleViewUpdate = new ConsoleViewUpdate();
            List<Object> entities = model.findEntities(clazz);
            for (Object entity : entities) {
                consoleViewUpdate.addMessage(entity);
            }
            view.changeState(ViewState.WAITING_USER_KEY);
            this.lastListRequest = request;
            ((ConsoleView) this.view).processConsoleViewUpdate(consoleViewUpdate);
        } catch (Exception ex) {
            logger.error("{}", ex);
            view.setCurrentOption(null);
        }
    }

    /**
     * Отправляет в представление сообщение из комплекта ресурсов
     * и возвращает обработку действий в меню
     *
     * @since 0.1
     * @param label
     */
    private void errorLabelMessage(String label){
        ConsoleViewUpdate update = new ConsoleViewUpdate();
        update.addMessage(label);
        view.changeState(ViewState.MENU);
        ((ConsoleView) this.view).processConsoleViewUpdate(update);
    }


    /**
     * Обрабатывает запрос из представления
     *
     * @param request объект запроса
     * @since 0.1
     */
    @Override
    public void processUserAction(ConsoleViewRequest request) {
        logger.debug("Processing request {}", request);
        Integer optionNumber = null;
        try{
            optionNumber = Integer.valueOf(request.getRequestMessage().trim());
        }
        catch (NumberFormatException ex){
            logger.error("Error when parsing option number {}", request.getRequestMessage().trim(), ex);
            errorLabelMessage("WrongOption");
            return;
        }
        ConsoleViewOptions option = ConsoleViewOptions.values()[optionNumber - 1];
        view.setCurrentOption(option);
        switch (option.getActionType()) {
            case SCROLLABLE:
                this.getScrollableList(option.getEntityClass(), request);
                break;
            case SHOW:
                this.getEntireList(option.getEntityClass(), request);
                break;
            case CREATE:
                this.createEntity(option.getEntityClass(), request.getBean());
                break;
            case UPDATE:
                this.updateEntity(option.getEntityClass(), request.getBean());
                break;
            case DELETE:
                this.deleteEntity(option.getEntityClass(), request.getId());
                break;
        }

    }

    private void createEntity(Class clazz, Object bean) {
        try {
            logger.debug("bean = {}", bean);
            model.createEntity(clazz, bean);
            ConsoleViewUpdate consoleViewUpdate = new ConsoleViewUpdate();
            consoleViewUpdate.addMessage("SucCreated");
            view.changeState(ViewState.MENU);
            ((ConsoleView) this.view).processConsoleViewUpdate(consoleViewUpdate);
        } catch (Exception ex) {
            errorLabelMessage("CreateError");
        }
    }

    private void deleteEntity(Class clazz, Serializable id) {
        try {
            logger.debug("id = {}", id);
            model.deleteEntity(clazz, id);
            ConsoleViewUpdate consoleViewUpdate = new ConsoleViewUpdate();
            consoleViewUpdate.addMessage("SucDeleted");
            view.changeState(ViewState.MENU);
            ((ConsoleView) this.view).processConsoleViewUpdate(consoleViewUpdate);
        } catch (IllegalArgumentException ex) {
            errorLabelMessage("IdNotExists");
        }
    }

    private void updateEntity(Class clazz, Object bean) {
        try {
            logger.debug("bean = {}", bean);
            model.updateEntity(clazz, bean);
            ConsoleViewUpdate consoleViewUpdate = new ConsoleViewUpdate();
            consoleViewUpdate.addMessage("SucUpdated");
            view.changeState(ViewState.MENU);
            ((ConsoleView) this.view).processConsoleViewUpdate(consoleViewUpdate);
        } catch (IllegalArgumentException ex){
            errorLabelMessage("IdNotExists");
        }
        catch (Exception ex) {
            errorLabelMessage("UpdateError");
        }
    }

    /**
     * Возвращает сущность по id
     *
     * @param clazz класс сущности
     * @param id    id
     * @return сущность с искомым id
     * @since 0.1
     */
    @Override
    public Object getEntity(Class clazz, Serializable id) {
        try{
            Object result =  model.getEntity(clazz, id);
            return result;
        }
        catch (IllegalArgumentException ex){
            errorLabelMessage("IdNotExists");
        }
        catch (Exception ex){
            errorLabelMessage("GetEntityError");
        }
        return null;
    }
}
