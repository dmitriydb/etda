package com.github.dmitriydb.etda.view.console;

import com.github.dmitriydb.etda.controller.EtdaController;
import com.github.dmitriydb.etda.controller.console.ConsoleController;
import com.github.dmitriydb.etda.view.EtdaView;

import java.util.Locale;
import java.util.Scanner;

public abstract class ConsoleView extends EtdaView {
    protected Scanner in = new Scanner(System.in);

    /**
     * Этот метод возвращает строку, корректную для вывода в соответствующем терминале (telnet, cmd...)
     * Каждый конкретный view должен переопределить данный метод
     * @return
     */
    protected abstract String line(String line);

    protected abstract void processInput();

    public abstract void processConsoleViewUpdate(ConsoleViewUpdate update);
}
