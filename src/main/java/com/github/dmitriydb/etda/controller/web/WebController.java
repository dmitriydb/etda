package com.github.dmitriydb.etda.controller.web;

import com.github.dmitriydb.etda.consoleapp.WindowsCmdUtil;
import com.github.dmitriydb.etda.model.SimpleModel;
import com.github.dmitriydb.etda.model.simplemodel.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.Serializable;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Суперкласс для всех контроллеров в веб-приложении
 * @version 0.2
 * @since 0.2
 */
public class WebController {

    /**
     * Содержит REST-маппинг
     * Загружается из properties
     */
    protected String mapping;

    /**
     * Класс, с которым работает контроллер
     */
    protected Class clazz;

    public WebController(String mapping, Class clazz) {
        this.mapping = mapping;
        this.clazz = clazz;
    }

    /**
     * Максимальное количество записей на 1 странице
     */
    @Value("${etda.page.size}")
    protected int maxPageSize;

    /**
     * Комплект ресурсов
     */
    @Autowired
    protected ResourceBundle resourceBundle;

    /**
     * Список сообщений, которые будут показаны на странице
     * Очищается после каждого вывода сообщений на страницу
     */
    protected List<String> messages = new ArrayList<>();

    /**
     * Добавляет в модель все сообщения из поля message
     * И очищает messages
     * @param model
     */
    protected void addMessages(Model model) {
        //объекты для заполнения

        model.addAttribute("employee", new Employee());
        model.addAttribute("department", new Department());
        model.addAttribute("title", new Title());
        model.addAttribute("salary", new Salary());
        model.addAttribute("manager", new DepartmentManager());

        //лейблы для интернационализации
        model.addAttribute("areYouSureToDelete", WindowsCmdUtil.convertToTerminalString(resourceBundle.getString("AreYouSureToDelete")));
        List<String> result = new ArrayList<>();
        for (String s : messages) {
            result.add(WindowsCmdUtil.convertToTerminalString(s));
        }
        model.addAttribute("messages", result);
        messages.clear();
    }

    /**
     * По номеру страницы возвращает величину смещения для выборки
     * @param page номер страницы, начинается с 1
     * @return
     */
    protected int getOffsetByPage(int page){
        return maxPageSize * (page - 1);
    }

    protected List<Object> getObjectList(Model model, String filter){
        List<Object> list;
        if (filter == null || filter.trim().equals("")){
            list = new SimpleModel().findEntities(clazz, maxPageSize, 0);
            model.addAttribute("filter", "");
            model.addAttribute("maxPages", new SimpleModel().countEntities(clazz) / maxPageSize);
        }
        else{
            list = new SimpleModel().findEntitiesFiltered(clazz, filter, maxPageSize, 0);
            model.addAttribute("filter", filter);
            model.addAttribute("maxPages", new SimpleModel().countEntitiesFiltered(clazz, filter) / maxPageSize);
        }
        return list;
    }

    protected String showList(Model model, String filter) {
        List<Object> list = getObjectList(model, filter);
        model.addAttribute(mapping, list);
        model.addAttribute("currentPage", 1);

        addMessages(model);
        return mapping;
    }

    protected String showList(List<Object> list, Model model, String filter) {
        model.addAttribute(mapping, list);
        model.addAttribute("currentPage", 1);
        addMessages(model);
        return mapping;
    }

    protected List<Object> getObjectListOnPage(Model model, int page, String filter){
        List<Object> list;
        if (filter == null || filter.trim().equals("")){
            list = new SimpleModel().findEntities(clazz, maxPageSize, getOffsetByPage(page));
            model.addAttribute("filter", "");
            model.addAttribute("maxPages", new SimpleModel().countEntities(clazz) / maxPageSize);
        }
        else{
            list = new SimpleModel().findEntitiesFiltered(clazz, filter, maxPageSize, getOffsetByPage(page));
            model.addAttribute("filter", filter);
            model.addAttribute("maxPages", new SimpleModel().countEntitiesFiltered(clazz, filter) / maxPageSize);
        }
        return list;
    }

    public String showPage(Model model, String pageNumber, String filter) {
        int page = Integer.valueOf(pageNumber).intValue();
        List<Object> list = getObjectListOnPage(model, page, filter);
        model.addAttribute(mapping, list);
        model.addAttribute("currentPage", page);
        addMessages(model);
        return mapping;
    }

    public String showPage(List<Object> list, Model model, String pageNumber, String filter) {
        int page = Integer.valueOf(pageNumber).intValue();
        model.addAttribute(mapping, list);
        model.addAttribute("currentPage", page);
        addMessages(model);
        return mapping;
    }

    public String deleteEntity(Model model, Serializable id, String pageNumber){
        int page = Integer.valueOf(pageNumber).intValue();
        new SimpleModel().deleteEntity(clazz, id);
        messages.add(resourceBundle.getString("SucDeleted"));
        model.addAttribute("currentPage", page);
        return "redirect:/" + mapping + "/" + page;
    }

    public String addEntity(Model model, Object entity){
        try{
            new SimpleModel().createEntity(clazz, entity);
        }
        catch (Exception ex){
            if (entity instanceof Salary){
                messages.add(resourceBundle.getString("SalaryAddError"));
            }
            else
            if (entity instanceof DepartmentManager){
                messages.add(resourceBundle.getString("ManagerAddError"));
            }
            else
            {
                messages.add(resourceBundle.getString("AddError"));
            }
            return "redirect:/" + mapping + "/1";

        }
        messages.add(resourceBundle.getString("SucAdded"));
        return "redirect:/" + mapping + "/1";
    }

    public String updateEntity(Model model, Object entity, String pageNumber){
        int page = Integer.valueOf(pageNumber).intValue();
        new SimpleModel().updateEntity(clazz, entity);
        messages.add(resourceBundle.getString("SucUpdated"));
        model.addAttribute("currentPage", page);
        return "redirect:/" + mapping + "/" + page;
    }
}
