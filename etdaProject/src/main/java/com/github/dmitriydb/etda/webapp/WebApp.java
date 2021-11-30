package com.github.dmitriydb.etda.webapp;


import com.github.dmitriydb.etda.dbutils.DbManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication
@EnableAutoConfiguration(exclude = { //
        DataSourceAutoConfiguration.class, //
        DataSourceTransactionManagerAutoConfiguration.class, //
        HibernateJpaAutoConfiguration.class })
public class WebApp {

    public static void main(String[] args){
        DbManager.init();
        try {
            SpringApplication.run(WebApp.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}