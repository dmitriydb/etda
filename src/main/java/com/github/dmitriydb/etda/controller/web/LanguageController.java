package com.github.dmitriydb.etda.controller.web;

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
    public String changeLanguage(@RequestParam String lang, Model model, SessionLocaleResolver resolver, HttpServletRequest request, HttpServletResponse response
    ){
        if (lang == null) return "redirect:/";
        if (lang.equals("ru"))
            resolver.setLocale(request, response, Locale.forLanguageTag("ru-RU"));
        else
        if (lang.equals("en"))
            resolver.setLocale(request, response, Locale.ENGLISH);
        return "redirect:/";
    }
}
