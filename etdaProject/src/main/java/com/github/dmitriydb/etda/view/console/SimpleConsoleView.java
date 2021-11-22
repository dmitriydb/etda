package com.github.dmitriydb.etda.view.console;

import com.github.dmitriydb.etda.consoleapp.WindowsCmdUtil;
import com.github.dmitriydb.etda.model.RegexConstraints;
import com.github.dmitriydb.etda.model.simplemodel.domain.Employee;
import com.github.dmitriydb.etda.resources.ResourceBundleManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Простое консольное представление для терминала Windows
 *
 * @version 0.1
 * @since 0.1
 */
public class SimpleConsoleView extends ConsoleView {

    private ConsoleViewRequest lastRequest;
    private boolean isProcessLastRequest = false;

    String dateFormat = "d.M.yyyy";

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
        isProcessLastRequest = false;

        if (this.currentState == ViewState.CREATED) {
            out.println(line(resourceBundle.getString("welcome")));
            processInput();
        } else if (this.currentState == ViewState.WAITING_USER_INPUT || this.currentState == ViewState.WAITING_CONTROLLER) {
            processInput();
        } else if (this.currentState == ViewState.MENU) {
            processInput();
        } else if (this.currentState == ViewState.WAITING_USER_KEY) {
            logger.debug("WAITING_USER_KEY");
            while (true) {
                if (!filter.equals("")) {
                    out.println("Current filter [" + filter + "]");
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
            switch (c) {
                case '+': {
                    this.setOffset(getOffset() + 1);
                    ConsoleViewRequest request = new ConsoleViewRequest(option, getOffset());
                    request.setFilter(filter);
                    processUserAction(request);
                    break;
                }
                case '-': {
                    this.setOffset(getOffset() - 1);
                    ConsoleViewRequest request = new ConsoleViewRequest(option, getOffset());
                    request.setFilter(filter);
                    processUserAction(request);
                    break;
                }
                case 'n': {
                    isProcessLastRequest = true;
                    createEmployee();
                    break;
                }
                case 'd': {
                    isProcessLastRequest = true;
                    deleteEmployee();
                    break;
                }
                case 'u': {
                    isProcessLastRequest = true;
                    updateEmployee();
                    break;
                }
                default: {
                    setOffset(0);
                    filter = s;
                    ConsoleViewRequest request = new ConsoleViewRequest(option, getOffset());
                    request.setFilter(filter);
                    processUserAction(request);
                }
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
            out.println(op);
        }
    }

    private boolean isDateValid(String date){
        try{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
            LocalDate d = LocalDate.parse(date, formatter);
            return true;
        }
        catch (Exception ex){
            return false;
        }
    }

    /**
     * Запрашивает дату у пользователя, пока он не введет корректную в региональном формате
     * Либо если он нажмет специальную клавишу выхода - вернет в меню
     * @return дата типа java.sql.date
     */
    private Date inputDate(){
        String sDate = in.nextLine();
        while (!isDateValid(sDate)){
            out.println(l("WrongDate"));
            out.println(l("DateFormat") + l(dateFormat));
            sDate = in.nextLine();
        }
        LocalDate date = LocalDate.parse(sDate, DateTimeFormatter.ofPattern(dateFormat));
        return Date.valueOf(date);
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
        out.println(l("EnterName"));
        String name = in.nextLine();
        while (!RegexConstraints.match(name, RegexConstraints.VALID_NAME_PATTERN)){
            out.println(l("WrongName"));
            name = in.nextLine();
        }
        e.setFirstName(name);
        out.println(l("EnterSurname"));
        String surname = in.nextLine();
        while (!RegexConstraints.match(surname, RegexConstraints.VALID_SURNAME_PATTERN)){
            out.println(l("WrongSurname"));
            surname = in.nextLine();
        }
        e.setLastName(surname);
        out.println(l("EnterBirthday"));
        Date birthday = inputDate();
        e.setBirth_date(birthday);
        out.println(l("EnterHireDate"));
        Date hireDate = inputDate();
        e.setHireDate(hireDate);
        out.println(l("EnterGender"));
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
        out.println(l("EnterLineNumber"));
        String line = in.nextLine();
        Long id = Long.valueOf(line);
        return id;
    }


    private void createEmployee(){
        Employee e = inputEmployee();
        if (e != null) {
            ConsoleViewRequest request = new ConsoleViewRequest(String.valueOf(ConsoleViewOptions.CREATE_EMPLOYEE.ordinal() + 1), 0);
            request.setBean(e);
            processUserAction(request);
        }
    }

    private void deleteEmployee(){
        Long id = getID();
        ConsoleViewRequest request = new ConsoleViewRequest(String.valueOf(ConsoleViewOptions.DELETE_EMPLOYEE.ordinal() + 1), 0);
        request.setId(id);
        processUserAction(request);
    }

    /**
     * Делегирует обработку запроса контроллеру
     * @param request
     */
    private void processUserAction(ConsoleViewRequest request){
        controller.processUserAction(request);
    }


    private void updateEmployee(){
        Long id = getID();
        Object result = controller.getEntity(Employee.class, id);
        if (result == null){
            out.println(l("IdNotExists"));
            processInput();
        }
        Employee e = (Employee) result;
        e = updateEmployee(e);
        ConsoleViewRequest request = new ConsoleViewRequest(String.valueOf(ConsoleViewOptions.UPDATE_EMPLOYEE.ordinal() + 1), 0);
        request.setBean(e);
        processUserAction(request);
    }

    /**
     * Обрабатывает выбор пользователем пункта основного меню
     */
    @Override
    protected void processInput() {
        filter = "";
        out.println(line(resourceBundle.getString("action")));
        listOptions();
        this.changeState(ViewState.WAITING_USER_INPUT);
        String line = in.nextLine();

        try {
            Integer i = Integer.valueOf(line);
            ConsoleViewOptions option = ConsoleViewOptions.values()[i - 1];
            switch (option) {
                case CREATE_EMPLOYEE: {
                    createEmployee();
                    break;
                }
                case DELETE_EMPLOYEE: {
                    deleteEmployee();
                    break;
                }
                case UPDATE_EMPLOYEE: {
                    updateEmployee();
                    break;
                }
                default: {
                    this.changeState(ViewState.WAITING_CONTROLLER);
                    ConsoleViewRequest request = new ConsoleViewRequest(line, 0);
                    lastRequest = request;
                    processUserAction(request);
                    break;
                }
            }
        } catch (NumberFormatException ex) {
            out.println(l("WrongMenuOption"));
            processInput();
        }
    }

    private String encase(String s){
        return "[" + s + "]";
    }

    private Employee updateEmployee(Employee original) {
        out.println(l("EnterName") + encase(original.getFirstName()));
        String name = in.nextLine();
        while (!RegexConstraints.match(name, RegexConstraints.VALID_NAME_PATTERN)){
            out.println(l("WrongName"));
            name = in.nextLine();
        }
        original.setFirstName(name);
        out.println(l("EnterSurname") + encase(original.getLastName()));
        String surname = in.nextLine();
        while (!RegexConstraints.match(surname, RegexConstraints.VALID_SURNAME_PATTERN)){
            out.println(l("WrongSurname"));
            surname = in.nextLine();
        }
        original.setLastName(surname);
        out.println(l("EnterBirthday") + encase(original.getBirth_date().toString()));
        Date birthday = inputDate();
        original.setBirth_date(birthday);
        out.println(l("EnterHireDate") + encase(original.getHireDate().toString()));
        Date hireDate = inputDate();
        original.setHireDate(hireDate);
        out.println(l("EnterGender") + "\n" + encase(original.getGender().toString()));
        String gender = in.nextLine();
        char c = gender.trim().equals("1") ? 'M' : 'F';
        original.setGender(c);
        return original;
    }

    @Override
    public void processConsoleViewUpdate(ConsoleViewUpdate update) {
        logger.debug("Пришел апдейт {}", line(update.toString()));
        cachedLines.clear();
        for (Object message : update.getMessages()) {
            if (message instanceof String)
                out.println(l(message.toString()));
            else
                out.println(message.toString());
            cachedLines.add(message);
        }

        if (isProcessLastRequest && lastRequest != null){
            logger.debug("last request = {}, isProcess = {}", lastRequest.toString(), isProcessLastRequest);
            isProcessLastRequest = false;
            this.processUserAction(lastRequest);
        }

        //out.format("[Показаны %d - %d]\n", update.getLeftPosition(), update.getRightPosition());
        updateSelf();
    }
}
