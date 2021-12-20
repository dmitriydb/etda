package com.github.dmitriydb.etda.controller.web;

import com.github.dmitriydb.etda.model.simplemodel.domain.DepartmentEmployeeSuite;
import com.github.dmitriydb.etda.model.simplemodel.domain.DepartmentManager;
import com.github.dmitriydb.etda.model.simplemodel.domain.Employee;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/managers")
    public String showManagers(Model model, @RequestParam(defaultValue = "") String filter) {
        return super.showList(model, filter);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/managers/{pageNumber}")
    public String showManagersPage(Model model, @PathVariable String pageNumber, @RequestParam(defaultValue = "") String filter) {
        return super.showPage(model, pageNumber, filter);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'HR')")
    @GetMapping("/managers/delete/{pageNumber}/{managerInfo}")
    public String deleteManager(Model model, @PathVariable String managerInfo, @PathVariable String pageNumber){
        int page = Integer.valueOf(pageNumber).intValue();
        String[] splits = managerInfo.split(":");
        Long empNo = Long.valueOf(splits[0]);
        String deptCode = splits[1];
        DepartmentEmployeeSuite suite = new DepartmentEmployeeSuite();
        suite.setDepartmentId(deptCode);
        suite.setEmployeeNumber(empNo);
        return super.deleteEntity(model, suite, pageNumber);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'HR')")
    @PostMapping("/managers/add")
    public String addManager(Model model, @ModelAttribute @Valid DepartmentManager manager, BindingResult result){
        if (result.hasErrors()){
            messages.add(resourceBundle.getString("InputError"));
            return "redirect:/" + mapping + "/1";
        }
        return super.addEntity(model, manager);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'HR')")
    @PostMapping("/managers/update")
    public String updateManager(Model model, @ModelAttribute DepartmentManager manager, @RequestParam("currentPage") String pageNumber){
        return super.updateEntity(model, manager, pageNumber);
    }
}
