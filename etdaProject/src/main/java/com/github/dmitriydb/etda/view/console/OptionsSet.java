package com.github.dmitriydb.etda.view.console;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс инкапсулирует все доступные опции в контексте одного меню
 *
 * Можно самостоятельно создавать объекты этого класса, но придется добавлять опции в список самому
 * Например, для конкретного пользователя с конкретной ролью нужно будет добавить только разрешенные ему опции
 *
 * @version 0.1.1
 * @since 0.1.1
 */
public class OptionsSet {
    protected List<ConsoleViewOptions> options = new ArrayList<>();

    private List<ConsoleViewOptions> getOptions() {
        return options;
    }

    /**
     * Метод возвращает объект опции в меню по строке
     * Для строки "1" будет возвращен нулевой элемент списка опций, для "2" 1-ый и так далее
     * Если номер не найден, то будет возвращен null
     * @param line
     * @return
     */
    public ConsoleViewOptions getOptionByString(String line){
        try{
            Integer i = Integer.parseInt(line.trim());
            if (i < 1 || i > options.size()) return null;
            return options.get(i - 1);
        }
        catch (NumberFormatException ex){
            return null;
        }
    }

    public void addOption(ConsoleViewOptions option){
        options.add(option);
    }

}
