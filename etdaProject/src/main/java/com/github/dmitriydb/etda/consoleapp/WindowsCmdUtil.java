package com.github.dmitriydb.etda.consoleapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

/**
 * @version 0.1
 * @since 0.1
 *
 * Класс, который используется для преобразования UTF-8 строк в кодировку терминала Windows
 * В основном нужен для того, чтобы корректно выводить на терминал русские символы
 * Используется в простом классе консольного представления {@link com.github.dmitriydb.etda.view.console.SimpleConsoleView}
 */
public class WindowsCmdUtil {

    /**
     * Экземпляры этого класса создавать не нужно
     */
    private WindowsCmdUtil(){

    }

    private static Logger logger = LoggerFactory.getLogger(WindowsCmdUtil.class);

    /**
     * Метод конвертирует Java строку в строку, пригодную для вывода на стандартный терминал Windows
     * @version 0.1
     * @since 0.1
     */
    public static String convertToTerminalString(String original){
        byte[] b = original.getBytes(StandardCharsets.ISO_8859_1);
        return new String(b);
    }

}
