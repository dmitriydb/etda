package com.github.dmitriydb.etda.controller.web;


import com.github.dmitriydb.etda.consoleapp.WindowsCmdUtil;
import com.github.dmitriydb.etda.model.SimpleModel;
import com.github.dmitriydb.etda.model.simplemodel.domain.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Controller
public class EmployeeController {

    @Value("${etda.page.size}")
    private int maxPageSize;

    @Autowired
    ResourceBundle resourceBundle;

    private List<String> messages = new ArrayList<>();



    @GetMapping("/employees")
    public String showEmployees(Model model, @RequestParam(defaultValue = "") String filter) {
        List<Object> list;
        if (filter == null || filter.trim().equals("")){
            list = new SimpleModel().findEntities(Employee.class, maxPageSize, 0);
            model.addAttribute("filter", "");
        }
        else{
            list = new SimpleModel().findEntitiesFiltered(Employee.class, filter, maxPageSize, 0);
            model.addAttribute("filter", filter);
        }
        model.addAttribute("employees", list);
        model.addAttribute("currentPage", 1);
        model.addAttribute("maxPages", list.size() / maxPageSize);
        addMessages(model);
        return "employees";
    }

    private void addMessages(Model model) {
        //объекты для заполнения
        model.addAttribute("employee", new Employee());

        //лейблы для интернационализации
        model.addAttribute("areYouSureToDelete", WindowsCmdUtil.convertToTerminalString(resourceBundle.getString("AreYouSureToDelete")));
        List<String> result = new ArrayList<>();
        for (String s : messages) {
            result.add(WindowsCmdUtil.convertToTerminalString(s));
        }
        model.addAttribute("messages", result);
        messages.clear();
    }


    @GetMapping("/employees/{pageNumber}")
    public String showEmployeesPage(Model model, @PathVariable String pageNumber, @RequestParam(defaultValue = "") String filter) {
        int page = Integer.valueOf(pageNumber).intValue();

        List<Object> list;
        if (filter == null || filter.trim().equals("")){
            list = new SimpleModel().findEntities(Employee.class, maxPageSize, getOffsetByPage(page));
            model.addAttribute("filter", "");
        }
        else{
            list = new SimpleModel().findEntitiesFiltered(Employee.class, filter, maxPageSize, getOffsetByPage(page));
            model.addAttribute("filter", filter);
        }
        model.addAttribute("employees", list);
        model.addAttribute("currentPage", page);
        model.addAttribute("maxPages", list.size() / maxPageSize);
        addMessages(model);
        return "employees";
    }

    private int getOffsetByPage(int page){
        return maxPageSize * (page - 1);
    }
    @GetMapping("/employees/delete/{pageNumber}/{empNumber}")
    public String deleteEmployee(Model model, @PathVariable String empNumber, @PathVariable String pageNumber){
        int page = Integer.valueOf(pageNumber).intValue();
        Long employeeNumber = Long.valueOf(empNumber);
        new SimpleModel().deleteEntity(Employee.class, employeeNumber);
        messages.add(resourceBundle.getString("SucDeleted"));
        model.addAttribute("currentPage", page);
        return "redirect:/employees/" + page;
    }

    @PostMapping("/employees/add")
    public String addEmployee(Model model, @ModelAttribute Employee employee){
        new SimpleModel().createEntity(Employee.class, employee);
        messages.add(resourceBundle.getString("SucAdded"));
        return "redirect:/employees/1";
    }

    @PostMapping("/employees/update")
    public String updateEmployee(Model model, @ModelAttribute Employee employee, @RequestParam("currentPage") String pageNumber){
        int page = Integer.valueOf(pageNumber).intValue();
        new SimpleModel().updateEntity(Employee.class, employee);
        messages.add(resourceBundle.getString("SucUpdated"));
        model.addAttribute("currentPage", page);
        return "redirect:/employees/" + page;
    }

}
