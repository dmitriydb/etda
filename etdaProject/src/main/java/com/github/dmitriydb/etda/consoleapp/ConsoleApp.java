package com.github.dmitriydb.etda.consoleapp;

import com.github.dmitriydb.etda.controller.console.ConsoleController;
import com.github.dmitriydb.etda.dbutils.DbManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @version 0.2
 * @since 0.1
 *
 * Точка входа в консольное приложение
 */
public class ConsoleApp {

    private static final Logger logger = LoggerFactory.getLogger(ConsoleApp.class);

    /**
     * Метод для запуска консольного приложения
     * @param manager класс, который создает SessionFactory
     * @param controller контроллер
     *
     * @since 0.2
     */
    public void start(DbManager manager, ConsoleController controller){
        manager.initSelf();
        controller.startWork();
    }

    public static void main(String[] args){
        DbManager.init();
        new ConsoleController().startWork();
    }
}
