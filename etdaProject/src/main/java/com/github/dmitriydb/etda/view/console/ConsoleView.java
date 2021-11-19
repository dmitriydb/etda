package com.github.dmitriydb.etda.view.console;

import com.github.dmitriydb.etda.controller.EtdaController;
import com.github.dmitriydb.etda.controller.console.ConsoleController;
import com.github.dmitriydb.etda.view.EtdaView;

import java.util.Locale;
import java.util.Scanner;

/**
 * Уровень консольного представления
 * Текущая реализация: {@link com.github.dmitriydb.etda.view.console.SimpleConsoleView}
 *
 * Возможные реализации в будущем: представление для telnet-а и на основе консольной библиотека (jcurses, lanterna и т.д)
 *
 * @version 0.1
 * @since 0.1
 */
public abstract class ConsoleView extends EtdaView {

    /**
     * Этот метод возвращает строку, корректную для вывода в соответствующем терминале (telnet, cmd...)
     * Каждый конкретный view должен переопределить данный метод
     * @return
     */
    protected abstract String line(String line);

    protected abstract void processInput();

    public abstract void processConsoleViewUpdate(ConsoleViewUpdate update);
}
