package com.github.dmitriydb.etda.view.console;

import com.github.dmitriydb.etda.controller.EtdaController;
import com.github.dmitriydb.etda.controller.console.ConsoleController;
import com.github.dmitriydb.etda.view.EtdaView;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.Scanner;

/**
 * Уровень консольного представления
 * Текущая реализация: {@link com.github.dmitriydb.etda.view.console.SimpleConsoleView}
 *
 * Возможные реализации в будущем: представление для telnet-а и на основе консольной библиотека (jcurses, lanterna и т.д)
 *
 * @version 0.1.1
 * @since 0.1
 */
public abstract class ConsoleView extends EtdaView {

    /**
     * Текущий набор опций в консольном представлении
     */
    private OptionsSet options;

    /**
     * Поток терминального ввода. По умолчанию - System.in
     */
    protected Scanner in = new Scanner(System.in);

    /**
     * Поток терминального вывода. По умолчанию - System.out
     */
    protected PrintStream out = System.out;

    /**
     * Этот метод возвращает строку, корректную для вывода в соответствующем терминале (telnet, cmd...)
     * Каждый конкретный view должен переопределить данный метод
     * @return
     *
     * @since 0.1
     */
    protected abstract String line(String line);

    protected abstract void processInput();

    public abstract void processConsoleViewUpdate(ConsoleViewUpdate update);

    public Scanner getIn() {
        return in;
    }

    public void setIn(Scanner in) {
        this.in = in;
    }

    public PrintStream getOut() {
        return out;
    }

    public void setOut(PrintStream out) {
        this.out = out;
    }

    public OptionsSet getOptions() {
        return options;
    }

    public void setOptions(OptionsSet options) {
        this.options = options;
    }
}
