package com.github.dmitriydb.etda.controller.web;

import com.github.dmitriydb.etda.model.simplemodel.domain.Department;
import com.github.dmitriydb.etda.model.simplemodel.domain.Employee;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class DepartmentsController extends WebController{
    public DepartmentsController() {
        super("departments", Department.class);
    }

    @GetMapping("/departments")
    public String showDepartments(Model model, @RequestParam(defaultValue = "") String filter) {
        return super.showList(model, filter);
    }

    @GetMapping("/departments/{pageNumber}")
    public String showDepartmentsPage(Model model, @PathVariable String pageNumber, @RequestParam(defaultValue = "") String filter) {
        return super.showPage(model, pageNumber, filter);
    }

    @GetMapping("/departments/delete/{pageNumber}/{empNumber}")
    public String deleteDepartment(Model model, @PathVariable String empNumber, @PathVariable String pageNumber){
        return super.deleteEntity(model, empNumber, pageNumber);
    }

    @PostMapping("/departments/add")
    public String addDepartment(Model model, @ModelAttribute @Valid Department dept, BindingResult result){
        if (result.hasErrors()){
            messages.add(resourceBundle.getString("InputError"));
            return "redirect:/" + mapping + "/1";
        }
        return super.addEntity(model, dept);
    }

    @PostMapping("/departments/update")
    public String updateDepartment(Model model, @ModelAttribute Department dept, @RequestParam("currentPage") String pageNumber){
        return super.updateEntity(model, dept, pageNumber);
    }
}
