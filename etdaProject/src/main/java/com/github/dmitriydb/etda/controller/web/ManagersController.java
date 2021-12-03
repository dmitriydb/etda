package com.github.dmitriydb.etda.controller.web;

import com.github.dmitriydb.etda.model.simplemodel.domain.DepartmentManager;
import com.github.dmitriydb.etda.model.simplemodel.domain.Employee;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class ManagersController extends WebController{
    public ManagersController() {
        super("managers", DepartmentManager.class);
    }

    @GetMapping("/managers")
    public String showManagers(Model model, @RequestParam(defaultValue = "") String filter) {
        return super.showList(model, filter);
    }

    @GetMapping("/managers/{pageNumber}")
    public String showManagersPage(Model model, @PathVariable String pageNumber, @RequestParam(defaultValue = "") String filter) {
        return super.showPage(model, pageNumber, filter);
    }

    @GetMapping("/managers/delete/{pageNumber}/{empNumber}")
    public String deleteManager(Model model, @PathVariable String empNumber, @PathVariable String pageNumber){
        int page = Integer.valueOf(pageNumber).intValue();
        Long employeeNumber = Long.valueOf(empNumber);
        return super.deleteEntity(model, employeeNumber, pageNumber);
    }

    @PostMapping("/managers/add")
    public String addManager(Model model, @ModelAttribute @Valid DepartmentManager manager, BindingResult result){
        if (result.hasErrors()){
            messages.add(resourceBundle.getString("InputError"));
            return "redirect:/" + mapping + "/1";
        }
        return super.addEntity(model, manager);
    }

    @PostMapping("/managers/update")
    public String updateManager(Model model, @ModelAttribute DepartmentManager manager, @RequestParam("currentPage") String pageNumber){
        System.out.println(manager);
        return super.updateEntity(model, manager, pageNumber);
    }
}
