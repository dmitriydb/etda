package com.github.dmitriydb.etda.webapp;

import com.github.dmitriydb.etda.controller.web.EmployeeController;
import com.github.dmitriydb.etda.controller.web.MainController;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static junitparams.JUnitParamsRunner.$;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class WebAppTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnDefaultMessage() throws Exception {
        this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Employees")))
                .andExpect(content().string(containsString("Departments")))
                .andExpect(content().string(containsString("Managers")))
                .andExpect(content().string(containsString("Salaries")))
                .andExpect(content().string(containsString("Titles")))
                .andExpect(content().string(containsString("Register")))
                .andExpect(content().string(containsString("Login")));
    }
}