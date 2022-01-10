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
public class SalariesController extends WebController{
    public SalariesController() {
        super("salaries", Salary.class);
    }

    private List<Object> transmuteListToDtoList(List<Object> list){
        SimpleDAO employeeDao = new SimpleDAO(Employee.class);
        List<Object> dtoList = new ArrayList<Object>();
        for (Object mo : list){
            Salary s = (Salary)mo;
            SalaryDTO dto = new SalaryDTO();
            dto.setSalary(s.getSalary());
            dto.setToDate(s.getToDate());
            dto.setSalaryOrder(s.getSalaryOrder());
            Employee e = (Employee) employeeDao.read(s.getSalaryOrder().getEmployeeNumber());
            String name = e.getLastName() + " " + e.getFirstName();
            dto.setName(name);
            dtoList.add(dto);
        }
        return dtoList;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/salaries")
    public String showSalaries(Model model, @RequestParam(defaultValue = "") String filter) {
        List<Object> list = super.getObjectList(model, filter);
        return super.showList(transmuteListToDtoList(list), model, filter);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/salaries/{pageNumber}")
    public String showSalariesPage(Model model, @PathVariable String pageNumber, @RequestParam(defaultValue = "") String filter) {
        int page = Integer.valueOf(pageNumber).intValue();
        List<Object> list = super.getObjectListOnPage(model, page, filter);
        return super.showPage(transmuteListToDtoList(list), model, pageNumber, filter);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'HR')")
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

    @PreAuthorize("hasAnyAuthority('ADMIN', 'HR')")
    @PostMapping("/salaries/add")
    public String addSalary(Model model, @ModelAttribute @Valid Salary salary, BindingResult result){
        if (result.hasErrors()){
            messages.add(resourceBundle.getString("InputError"));
            return "redirect:/" + mapping + "/1";
        }
        return super.addEntity(model, salary);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'HR')")
    @PostMapping("/salaries/update")
    public String updateSalary(Model model, @ModelAttribute Salary salary, @RequestParam("currentPage") String pageNumber){
        return super.updateEntity(model, salary, pageNumber);
    }

}
