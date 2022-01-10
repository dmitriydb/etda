package com.github.dmitriydb.etda.controller.web;

import com.github.dmitriydb.etda.model.SimpleModel;
import com.github.dmitriydb.etda.model.simplemodel.dao.SimpleDaoFactory;
import com.github.dmitriydb.etda.model.simplemodel.domain.Employee;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainController {

    @GetMapping("/")
    public String mainPage() {
        return "index";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }


}