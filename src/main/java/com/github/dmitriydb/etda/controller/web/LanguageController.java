package com.github.dmitriydb.etda.controller.web;

import com.github.dmitriydb.etda.model.dao.SimpleDAO;
import com.github.dmitriydb.etda.model.dao.UserDAO;
import com.github.dmitriydb.etda.security.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

@Controller
public class LanguageController {

    @GetMapping("/hello")
    public String handleHello(@RequestParam(name = "user", defaultValue = "user", required = false) String user, Model model
                              ){
        model.addAttribute("user", user);
        return "hello";
    }

    @GetMapping("/language")
    public String changeLanguage(@AuthenticationPrincipal UserDetails userDetails, @RequestParam String lang, Model model, SessionLocaleResolver resolver, HttpServletRequest request, HttpServletResponse response
    ){



        User u = new UserDAO().getUserByName(userDetails.getUsername());

        if (lang == null) return "redirect:/";
        if (lang.equals("ru"))
        {
            resolver.setLocale(request, response, Locale.forLanguageTag("ru-RU"));
            u.setLocale(Locale.forLanguageTag("ru-RU"));
            new UserDAO().updateUser(u);
        }
        else
        if (lang.equals("en"))
        {
            resolver.setLocale(request, response, Locale.ENGLISH);
            u.setLocale(Locale.ENGLISH);
            new UserDAO().updateUser(u);
        }
        return "redirect:/";
    }
}
