package com.github.dmitriydb.etda.controller.web;

import com.github.dmitriydb.etda.model.dao.SimpleDAO;
import com.github.dmitriydb.etda.model.simplemodel.domain.DepartmentEmployeeSuite;
import com.github.dmitriydb.etda.model.simplemodel.domain.DepartmentManager;
import com.github.dmitriydb.etda.model.simplemodel.domain.DepartmentManagerDTO;
import com.github.dmitriydb.etda.model.simplemodel.domain.Employee;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ManagersController extends WebController{
    public ManagersController() {
        super("managers", DepartmentManager.class);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/managers")
    public String showManagers(Model model, @RequestParam(defaultValue = "") String filter) {
        List<Object> list = super.getObjectList(model, filter);
        return super.showList(transmuteListToDtoList(list), model, filter);
    }

    private List<Object> transmuteListToDtoList(List<Object> list){
        SimpleDAO employeeDao = new SimpleDAO(Employee.class);
        List<Object> dtoList = new ArrayList<Object>();
        for (Object mo : list){
            DepartmentManager m = (DepartmentManager)mo;
            DepartmentManagerDTO dto = new DepartmentManagerDTO();
            dto.setDepartmentManagerSuite(m.getDepartmentManagerSuite());
            dto.setFromDate(m.getFromDate());
            dto.setToDate(m.getToDate());
            Employee e = (Employee) employeeDao.read(dto.getDepartmentManagerSuite().getEmployeeNumber());
            String name = e.getLastName() + " " + e.getFirstName();
            dto.setName(name);
            dtoList.add(dto);
        }
        return dtoList;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/managers/{pageNumber}")
    public String showManagersPage(Model model, @PathVariable String pageNumber, @RequestParam(defaultValue = "") String filter) {
        int page = Integer.valueOf(pageNumber).intValue();
        List<Object> list = super.getObjectListOnPage(model, page, filter);
        return super.showPage(transmuteListToDtoList(list), model, pageNumber, filter);
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
