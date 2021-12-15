package com.github.dmitriydb.etda.webapp;

import com.github.dmitriydb.etda.controller.web.EmployeeController;
import com.github.dmitriydb.etda.controller.web.MainController;
import com.github.dmitriydb.etda.dbutils.DbManager;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.ModelResultMatchers;

import static junitparams.JUnitParamsRunner.$;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class WebAppTest {

    static {
        DbManager.init();
    }

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void mainPage() throws Exception {
        this.mockMvc.perform(get("/")).andExpect(status().isOk());
    }

    @Test
    public void commonAttributesAreEverywhere() throws Exception {
        String[] testCases = new String[]{"employees", "departments", "titles", "managers"};
        for (String s : testCases){
            this.mockMvc.perform(get("/" + s))
                    .andExpect(model().attributeExists(s.substring(0, s.length() - 1)))
                    .andExpect(model().attributeExists("messages"))
                    .andExpect(model().attributeExists("filter"))
                    .andExpect(model().attributeExists("maxPages"))
                    .andExpect(model().attributeExists("currentPage"));
        }
    }

    @Test
    public void whenGoingToPageTheCurrentPageIsPresent() throws Exception {
        String[] testCases = new String[]{"employees", "departments", "titles", "managers"};
        for (String s : testCases){
            this.mockMvc.perform(get("/" + s + "/2"))
                    .andExpect(model().attribute("currentPage", 2));
        }
    }

    @Test
    public void whenThereIsNoFilterFilterIsEmpty() throws  Exception{
        String[] testCases = new String[]{"employees", "departments", "titles", "managers"};
        for (String s : testCases){
            this.mockMvc.perform(get("/" + s))
                    .andExpect(model().attribute("filter", ""));
        }
    }

    @Test
    public void whenFilterIsSetItIsPresentInModel() throws  Exception{
        String[] testCases = new String[]{"employees", "departments", "titles", "managers"};
        for (String s : testCases){
            this.mockMvc.perform(get("/" + s + "/1?filter=filter"))
                    .andExpect(model().attribute("filter", "filter"));
        }
    }

}