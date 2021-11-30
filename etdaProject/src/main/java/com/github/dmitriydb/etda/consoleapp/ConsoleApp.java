package com.github.dmitriydb.etda.consoleapp;

import com.github.dmitriydb.etda.controller.console.ConsoleController;
import com.github.dmitriydb.etda.dbutils.DbManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @version 0.1
 * @since 0.1
 *
 * Точка входа в консольное приложение
 */
public class ConsoleApp {

    private static final Logger logger = LoggerFactory.getLogger(ConsoleApp.class);
    public static void main(String[] args){
        DbManager.init();
        new ConsoleController().startWork();
    }
}
