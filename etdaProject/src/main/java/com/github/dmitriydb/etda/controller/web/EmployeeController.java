package com.github.dmitriydb.etda.controller.web;


import com.github.dmitriydb.etda.model.SimpleModel;
import com.github.dmitriydb.etda.model.simplemodel.domain.Employee;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class EmployeeController {

    @Value("${etda.page.size}")
    private int maxPageSize;

    @GetMapping("/employees")
    public String showEmployees(Model model) {
        List<Object> list = new SimpleModel().findEntities(Employee.class, maxPageSize, 0);
        model.addAttribute("employees", list);
        model.addAttribute("currentPage", 1);
        return "employees";
    }

    @GetMapping("/employees/{id}")
    public String showEmployeesPage(Model model, @PathVariable String id) {
        int offset = Integer.valueOf(id).intValue();
        List<Object> list = new SimpleModel().findEntities(Employee.class, maxPageSize, maxPageSize * (offset - 1));
        model.addAttribute("employees", list);
        model.addAttribute("currentPage", offset);
        return "employees";
    }



}
