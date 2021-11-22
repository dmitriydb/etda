package com.github.dmitriydb.etda.view.console;

import com.github.dmitriydb.etda.consoleapp.WindowsCmdUtil;
import com.github.dmitriydb.etda.controller.console.ConsoleController;
import com.github.dmitriydb.etda.dbutils.DbManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class SimpleConsoleViewTest {

    private ConsoleController controller;
    private ConsoleView view;

    @BeforeAll
    public static void initController(){
        DbManager.init();
    }

    @BeforeEach
    public void initControllerAndView(){
        controller = new ConsoleController();
        ConsoleView view = (ConsoleView) controller.getView();
    }

    @Test
    public void connectionOK(){
        view.setIn(new Scanner("1\n"));

        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final String utf8 = StandardCharsets.UTF_8.name();
        try (PrintStream ps = new PrintStream(baos, true, utf8)) {
            view.setOut(ps);
            controller.startWork();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            String data = baos.toString(utf8);
            String[] splits = data.split("\\n");

            assertEquals(splits.length, 44);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }



}