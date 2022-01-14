package com.github.dmitriydb.etda.controller.web;

import com.github.dmitriydb.etda.model.dao.SimpleDAO;
import com.github.dmitriydb.etda.model.simplemodel.domain.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TitlesController extends WebController{
    public TitlesController() {
        super("titles", Title.class);
    }

    private List<Object> transmuteListToDtoList(List<Object> list){
        SimpleDAO employeeDao = new SimpleDAO(Employee.class);
        List<Object> dtoList = new ArrayList<Object>();
        for (Object mo : list){
            Title t = (Title)mo;
            TitleDTO dto = new TitleDTO();
            dto.setTitleOrder(t.getTitleOrder());
            dto.setToDate(t.getToDate());
            Employee e = (Employee) employeeDao.read(t.getTitleOrder().getEmployeeNumber());
            String name = e.getLastName() + " " + e.getFirstName();
            dto.setName(name);
            dtoList.add(dto);
        }
        return dtoList;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/titles")
    public String showTitles(Model model, @RequestParam(defaultValue = "") String filter) {
        List<Object> list = super.getObjectList(model, filter);
        return super.showList(transmuteListToDtoList(list), model, filter);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/titles/{pageNumber}")
    public String showTitlesPage(Model model, @PathVariable String pageNumber, @RequestParam(defaultValue = "") String filter) {
        int page = Integer.valueOf(pageNumber).intValue();
        List<Object> list = super.getObjectListOnPage(model, page, filter);
        return super.showPage(transmuteListToDtoList(list), model, pageNumber, filter);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'HR')")
    @GetMapping("/titles/delete/{pageNumber}/{titleInfo}")
    public String deleteTitle(Model model, @PathVariable String titleInfo, @PathVariable String pageNumber){
        int page = Integer.valueOf(pageNumber).intValue();
        String[] splits = titleInfo.split(":");
        Long empNo = Long.valueOf(splits[0]);
        String title = splits[1];
        Date date = Date.valueOf(splits[2]);
        TitleOrder order = new TitleOrder();
        order.setTitle(title);
        order.setFromDate(date);
        order.setEmployeeNumber(empNo);
        return super.deleteEntity(model, order, pageNumber);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'HR')")
    @PostMapping("/titles/add")
    public String addTitle(Model model, @ModelAttribute @Valid Title title, BindingResult result){
        if (result.hasErrors()){
            messages.add(resourceBundle.getString("InputError"));
            return "redirect:/" + mapping + "/1";
        }
        if (title.getTitleOrder().getFromDate().after(title.getToDate())){
            messages.add(resourceBundle.getString("DateFromToError"));
            return "redirect:/" + mapping + "/1";
        }
        return super.addEntity(model, title);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'HR')")
    @PostMapping("/titles/update")
    public String updateTitle(Model model, @ModelAttribute Title title, BindingResult result, @RequestParam("currentPage") String pageNumber){
        if (result.hasErrors()){
            messages.add(resourceBundle.getString("UpdateError"));
            return "redirect:/" + mapping + "/1";
        }
        return super.updateEntity(model, title, pageNumber);
    }

}
