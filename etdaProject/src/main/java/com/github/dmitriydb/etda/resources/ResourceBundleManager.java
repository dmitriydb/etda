package com.github.dmitriydb.etda.resources;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Класс содержит комплекты ресурсов для разных компонентов приложения
 *
 * @version 0.1
 * @since 0.1
 */
public class ResourceBundleManager {
    public static ResourceBundle getConsoleResourceBundle(){
        return ResourceBundle.getBundle("console");
    }
}
