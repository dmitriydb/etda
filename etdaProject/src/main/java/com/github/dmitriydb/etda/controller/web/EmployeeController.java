package com.github.dmitriydb.etda.controller.web;


import com.github.dmitriydb.etda.consoleapp.WindowsCmdUtil;
import com.github.dmitriydb.etda.model.SimpleModel;
import com.github.dmitriydb.etda.model.simplemodel.domain.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


@Controller
public class EmployeeController extends WebController{

    public EmployeeController() {
        super("employees", Employee.class);
    }

    @GetMapping("/employees")
    public String showEmployees(Model model, @RequestParam(defaultValue = "") String filter) {
       return super.showList(model, filter);
    }

    @GetMapping("/employees/{pageNumber}")
    public String showEmployeesPage(Model model, @PathVariable String pageNumber, @RequestParam(defaultValue = "") String filter) {
        return super.showPage(model, pageNumber, filter);
    }


    @GetMapping("/employees/delete/{pageNumber}/{empNumber}")
    public String deleteEmployee(Model model, @PathVariable String empNumber, @PathVariable String pageNumber){
        int page = Integer.valueOf(pageNumber).intValue();
        Long employeeNumber = Long.valueOf(empNumber);
        return super.deleteEntity(model, employeeNumber, pageNumber);
    }

    @PostMapping("/employees/add")
    public String addEmployee(Model model, @ModelAttribute @Valid Employee employee, BindingResult result){
        if (result.hasErrors()){
            messages.add(resourceBundle.getString("InputError"));
            return "redirect:/" + mapping + "/1";
        }
        return super.addEntity(model, employee);
    }

    @PostMapping("/employees/update")
    public String updateEmployee(Model model, @ModelAttribute Employee employee, @RequestParam("currentPage") String pageNumber){
        return super.updateEntity(model, employee, pageNumber);
    }
}
