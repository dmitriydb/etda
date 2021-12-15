package com.github.dmitriydb.etda.consoleapp;

import com.github.dmitriydb.etda.controller.console.ConsoleController;
import com.github.dmitriydb.etda.dbutils.DbManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

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
