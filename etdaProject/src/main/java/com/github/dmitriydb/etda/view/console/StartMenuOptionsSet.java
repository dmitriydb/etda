package com.github.dmitriydb.etda.view.console;

/**
 * Класс содержит опции для начального меню консольного приложения (регистрация, логин, выход и т.д)
 *
 * @version 0.1.1
 * @since 0.1.1
 */
public class StartMenuOptionsSet extends OptionsSet{
    public StartMenuOptionsSet(){
        options.add(ConsoleViewOptions.LOGIN);
        options.add(ConsoleViewOptions.REGISTER);
        options.add(ConsoleViewOptions.EXIT);
    }
}
