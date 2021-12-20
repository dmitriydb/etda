package com.github.dmitriydb.etda.controller.web;

import com.github.dmitriydb.etda.model.dao.UserDAO;
import com.github.dmitriydb.etda.security.SecurityManager;
import com.github.dmitriydb.etda.security.SecurityRole;
import com.github.dmitriydb.etda.security.User;
import com.github.dmitriydb.etda.security.UserDTO;
import com.github.dmitriydb.etda.webapp.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class RegisterController {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @GetMapping("/register")
    public String showRegister(Model model){
        model.addAttribute("user", new UserDTO());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(Model model, @ModelAttribute("user") @Valid UserDTO userDTO, BindingResult bindingResult){

        if (!userDTO.getPassword().equals(userDTO.getMatchingPassword())){
            bindingResult.addError(new FieldError("user", "matchingPassword", "Пароли не совпадают"));
        }

        if (new UserDAO().isUserExists(userDTO.getUsername())){
            bindingResult.addError(new FieldError("user", "username", "Такое имя уже существует"));
        }

        if(bindingResult.hasErrors()){
            model.addAttribute("user", userDTO);
            return "register";
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("EMPLOYEE"));

        User u = new User();
        u.setName(userDTO.getUsername());
        u.setPassword(SecurityManager.hashPassword(userDTO.getPassword()));
        u.setEmail(userDTO.getEmail());
        u.setSecurityRole(SecurityRole.EMPLOYEE_ROLE());
        new UserDAO().createUser(u);

        UserDetails user = new org.springframework.security.core.userdetails.User( u.getName(), u.getPassword(), true, true, true, true, authorities);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user, user.getPassword(), authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "index";
    }
}
