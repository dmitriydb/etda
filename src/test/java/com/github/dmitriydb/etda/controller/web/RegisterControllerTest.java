package com.github.dmitriydb.etda.controller.web;

import com.github.dmitriydb.etda.webapp.WebApp;
import com.github.dmitriydb.etda.webapp.service.UserDetailsServiceImpl;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = WebApp.class)
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class RegisterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void shouldShowSalariesForAdmin() throws Exception {
        this.mockMvc.perform(get("/")).andExpect(status().isOk())
                .andExpect(content().string(containsString("Salaries")));
    }

    @Test
    @WithAnonymousUser
    public void shouldShowRegisterAndLoginForAnonymousUserAndNothingElse() throws Exception {
        this.mockMvc.perform(get("/")).andExpect(status().isOk())
                .andExpect(content().string(not(containsString("Employees"))))
                .andExpect(content().string(not(containsString("Salaries"))))
                .andExpect(content().string(not(containsString("Titles"))))
                .andExpect(content().string(not(containsString("Departments"))))
                .andExpect(content().string(not(containsString("Managers"))))
                .andExpect(content().string(containsString("Register")))
                .andExpect(content().string(containsString("Login")));
    }

}