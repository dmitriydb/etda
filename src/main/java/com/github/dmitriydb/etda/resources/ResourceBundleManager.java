package com.github.dmitriydb.etda.resources;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Класс содержит комплекты ресурсов для разных компонентов приложения
 *
 * @version 0.2
 * @since 0.1.2
 */
@Configuration
public class ResourceBundleManager {
    public static ResourceBundle getConsoleResourceBundle(){
        return ResourceBundle.getBundle("console");
    }

    /**
     * @param locale
     * @return
     * @since 0.1.2
     */
    public static ResourceBundle getConsoleResourceBundle(Locale locale){
        return ResourceBundle.getBundle("console", locale);
    }


    public static ResourceBundle getWebResourceBundle(Locale locale){
        return ResourceBundle.getBundle("web", locale);
    }

    @Bean
    public static ResourceBundle getWebResourceBundle(){
        return ResourceBundle.getBundle("web", Locale.forLanguageTag("ru-RU"));
    }
}
