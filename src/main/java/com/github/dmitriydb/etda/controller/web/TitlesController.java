package com.github.dmitriydb.etda.controller.web;

import com.github.dmitriydb.etda.model.simplemodel.domain.DepartmentEmployeeSuite;
import com.github.dmitriydb.etda.model.simplemodel.domain.DepartmentManager;
import com.github.dmitriydb.etda.model.simplemodel.domain.Title;
import com.github.dmitriydb.etda.model.simplemodel.domain.TitleOrder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;

@Controller
public class TitlesController extends WebController{
    public TitlesController() {
        super("titles", Title.class);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/titles")
    public String showTitles(Model model, @RequestParam(defaultValue = "") String filter) {
        return super.showList(model, filter);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/titles/{pageNumber}")
    public String showTitlesPage(Model model, @PathVariable String pageNumber, @RequestParam(defaultValue = "") String filter) {
        return super.showPage(model, pageNumber, filter);
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
        return super.addEntity(model, title);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'HR')")
    @PostMapping("/titles/update")
    public String updateTitle(Model model, @ModelAttribute Title title, @RequestParam("currentPage") String pageNumber){
        return super.updateEntity(model, title, pageNumber);
    }

}
