package com.github.dmitriydb.etda.view.console;

import com.github.dmitriydb.etda.consoleapp.WindowsCmdUtil;
import com.github.dmitriydb.etda.controller.EtdaController;
import com.github.dmitriydb.etda.model.simplemodel.domain.Employee;
import com.github.dmitriydb.etda.resources.ResourceBundleManager;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.text.View;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Простое консольное представление для терминала Windows
 *
 * @version 0.1
 * @since 0.1
 */
public class SimpleConsoleView extends ConsoleView {

    private Scanner in = new Scanner(System.in);

    //сохраненные объекты из прошлого обновления от контроллера
    private ArrayList<Object> cachedLines = new ArrayList<>();

    //текущая строка фильтра
    private String filter = "";

    private static Logger logger = LoggerFactory.getLogger(SimpleConsoleView.class);

    public SimpleConsoleView() {
        this.resourceBundle = ResourceBundleManager.getConsoleResourceBundle();
    }

    @Override
    public void updateSelf() {
        if (this.currentState == ViewState.CREATED) {
            System.out.println(line(resourceBundle.getString("welcome")));
            processInput();
        } else if (this.currentState == ViewState.WAITING_USER_INPUT || this.currentState == ViewState.WAITING_CONTROLLER) {
            processInput();
        } else if (this.currentState == ViewState.MENU) {
            processInput();
        } else if (this.currentState == ViewState.WAITING_USER_KEY) {
            logger.debug("WAITING_USER_KEY");
            while (true) {
                if (!filter.equals("")) {
                    System.out.println("Current filter [" + filter + "]");
                }
                processSingleCharacter();
            }
        }
    }

    /**
     * считывает символ с терминала и делегирует выполнение команды, привязанной к этому символу,
     * методу processCommand
     *
     * Используется в ответ на ввод пользователя на экране со списком данных
     */
    private void processSingleCharacter() {
        String s = in.nextLine();
        if (!s.trim().equals(""))
            processCommand(s);
    }

    /**
     * Обрабатывает первый символ строки и выполняет команду, привязанную к этому символу
     *
     * Используется в ответ на ввод пользователя на экране со списком данных
     * @param s
     */
    private void processCommand(String s) {
        logger.debug("Current option = {}", this.currentOption);
        char c = s.charAt(0);

        if (c == 'c') {
            this.setOffset(0);
            processInput();
        } else if (this.currentOption.getActionType().equals(ConsoleActionType.SCROLLABLE)) {
            String option = String.valueOf(currentOption.ordinal() + 1);
            if (c == '+') {
                this.setOffset(getOffset() + 1);
                ConsoleViewRequest request = new ConsoleViewRequest(option, getOffset());
                request.setFilter(filter);
                controller.processUserAction(request);
            } else if (c == '-') {
                this.setOffset(getOffset() - 1);
                ConsoleViewRequest request = new ConsoleViewRequest(option, getOffset());
                request.setFilter(filter);
                controller.processUserAction(request);
            } else {
                setOffset(0);
                filter = s;
                ConsoleViewRequest request = new ConsoleViewRequest(option, getOffset());
                request.setFilter(filter);
                controller.processUserAction(request);

            }
        }
    }

    /**
     * Перечисляет все доступные опции меню
     */
    private void listOptions() {
        for (ConsoleViewOptions option : ConsoleViewOptions.values()) {
            String optionName = line(option.getOptionLabel());
            Integer seqNumber = option.ordinal() + 1;
            String op = String.format("%d) %s", seqNumber, line(resourceBundle.getString(optionName)));
            System.out.println(op);
        }
    }

    @Override
    protected String line(String line) {
        return WindowsCmdUtil.convertToTerminalString(line);
    }

    /**
     * Запрашивает у пользователя данные и создает объект класса Employee
     * для дальнейшей передачи в контроллер для создания в БД
     * @return объект типа Employee
     */
    private Employee inputEmployee() {
        Employee e = new Employee();
        System.out.println(l("EnterName"));
        String name = in.nextLine();
        e.setFirstName(name);
        System.out.println(l("EnterSurname"));
        String surname = in.nextLine();
        e.setLastName(surname);
        System.out.println(l("EnterBirthday"));
        String birthday = in.nextLine();
        e.setBirth_date(Date.valueOf(birthday));
        System.out.println(l("EnterHireDate"));
        String hireDate = in.nextLine();
        e.setHireDate(Date.valueOf(hireDate));
        System.out.println(l("EnterGender"));
        String gender = in.nextLine();
        char c = gender.trim().equals("1") ? 'M' : 'F';
        e.setGender(c);
        return e;
    }

    /**
     * Получает строку из текстовых ресурсов по лейблу
     * @param label
     * @return
     */
    private String l(String label) {
        return line(resourceBundle.getString(label));
    }

    /**
     * Получает и возвращает введенный пользователем ID записи
     * @return корректное число типа Long
     */
    private Long getID() {
        System.out.println(l("EnterLineNumber"));
        String line = in.nextLine();
        Long id = Long.valueOf(line);
        return id;
    }

    /**
     * Обрабатывает выбор пользователем пункта основного меню
     */
    @Override
    protected void processInput() {
        filter = "";
        System.out.println(line(resourceBundle.getString("action")));
        listOptions();
        this.changeState(ViewState.WAITING_USER_INPUT);
        String line = in.nextLine();

        try {
            Integer i = Integer.valueOf(line);
            ConsoleViewOptions option = ConsoleViewOptions.values()[i - 1];
            switch (option) {
                case CREATE_EMPLOYEE: {
                    Employee e = inputEmployee();
                    if (e != null) {
                        ConsoleViewRequest request = new ConsoleViewRequest(line, 0);
                        request.setBean(e);
                        controller.processUserAction(request);
                    }
                    break;
                }
                case DELETE_EMPLOYEE: {
                    Long id = getID();
                    ConsoleViewRequest request = new ConsoleViewRequest(line, 0);
                    request.setId(id);
                    controller.processUserAction(request);
                    break;
                }
                case UPDATE_EMPLOYEE: {
                    Long id = getID();
                    Employee e = (Employee) controller.getEntity(Employee.class, id);
                    e = updateEmployee(e);
                    ConsoleViewRequest request = new ConsoleViewRequest(line, 0);
                    request.setBean(e);
                    controller.processUserAction(request);
                    break;
                }
                default: {
                    this.changeState(ViewState.WAITING_CONTROLLER);
                    controller.processUserAction(new ConsoleViewRequest(line, 0));
                    break;
                }
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            processInput();
        }

    }

    private String encase(String s){
        return "[" + s + "]";
    }

    private Employee updateEmployee(Employee original) {
        System.out.println(l("EnterName") + encase(original.getFirstName()));
        String name = in.nextLine();
        original.setFirstName(name);
        System.out.println(l("EnterSurname") + encase(original.getLastName()));
        String surname = in.nextLine();
        original.setLastName(surname);
        System.out.println(l("EnterBirthday") + encase(original.getBirth_date().toString()));
        String birthday = in.nextLine();
        original.setBirth_date(Date.valueOf(birthday));
        System.out.println(l("EnterHireDate") + encase(original.getHireDate().toString()));
        String hireDate = in.nextLine();
        original.setHireDate(Date.valueOf(hireDate));
        System.out.println(l("EnterGender") + "\n" + encase(original.getGender().toString()));
        String gender = in.nextLine();
        char c = gender.trim().equals("1") ? 'M' : 'F';
        original.setGender(c);
        return original;
    }

    @Override
    public void processConsoleViewUpdate(ConsoleViewUpdate update) {
        logger.debug("Пришел апдейт {}", update);
        cachedLines.clear();
        for (Object message : update.getMessages()) {
            System.out.println(message);
            cachedLines.add(message);
        }
        //System.out.format("[Показаны %d - %d]\n", update.getLeftPosition(), update.getRightPosition());
        updateSelf();
    }
}
