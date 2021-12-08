package com.github.dmitriydb.etda.controller.web;

import com.github.dmitriydb.etda.model.simplemodel.domain.Salary;
import com.github.dmitriydb.etda.model.simplemodel.domain.SalaryOrder;
import com.github.dmitriydb.etda.model.simplemodel.domain.Title;
import com.github.dmitriydb.etda.model.simplemodel.domain.TitleOrder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;

@Controller
public class SalariesController extends WebController{
    public SalariesController() {
        super("salaries", Salary.class);
    }

    @GetMapping("/salaries")
    public String showSalaries(Model model, @RequestParam(defaultValue = "") String filter) {
        return super.showList(model, filter);
    }

    @GetMapping("/salaries/{pageNumber}")
    public String showSalariesPage(Model model, @PathVariable String pageNumber, @RequestParam(defaultValue = "") String filter) {
        return super.showPage(model, pageNumber, filter);
    }

    @GetMapping("/salaries/delete/{pageNumber}/{salaryInfo}")
    public String deleteSalary(Model model, @PathVariable String salaryInfo, @PathVariable String pageNumber){
        int page = Integer.valueOf(pageNumber).intValue();
        String[] splits = salaryInfo.split(":");
        Long empNo = Long.valueOf(splits[0]);
        Date date = Date.valueOf(splits[1]);
        SalaryOrder order = new SalaryOrder();
        order.setFromDate(date);
        order.setEmployeeNumber(empNo);
        return super.deleteEntity(model, order, pageNumber);
    }

    @PostMapping("/salaries/add")
    public String addSalary(Model model, @ModelAttribute @Valid Salary salary, BindingResult result){
        if (result.hasErrors()){
            messages.add(resourceBundle.getString("InputError"));
            return "redirect:/" + mapping + "/1";
        }
        return super.addEntity(model, salary);
    }

    @PostMapping("/salaries/update")
    public String updateSalary(Model model, @ModelAttribute Salary salary, @RequestParam("currentPage") String pageNumber){
        return super.updateEntity(model, salary, pageNumber);
    }

}
