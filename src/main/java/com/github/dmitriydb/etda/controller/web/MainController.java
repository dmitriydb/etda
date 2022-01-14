package com.github.dmitriydb.etda.controller.web;

import com.github.dmitriydb.etda.model.SimpleModel;
import com.github.dmitriydb.etda.model.dao.UserDAO;
import com.github.dmitriydb.etda.model.simplemodel.dao.SimpleDaoFactory;
import com.github.dmitriydb.etda.model.simplemodel.domain.Employee;
import com.github.dmitriydb.etda.security.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class MainController {

    @GetMapping("/")
    public String mainPage(@AuthenticationPrincipal UserDetails details, SessionLocaleResolver resolver, HttpServletRequest request, HttpServletResponse response ) {
        if (details != null){
            User u = new UserDAO().getUserByName(details.getUsername());
            resolver.setLocale(request, response, u.getLocale());
        }
        return "index";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

}