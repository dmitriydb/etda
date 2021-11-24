package com.github.dmitriydb.etda.view.console;

import com.github.dmitriydb.etda.consoleapp.WindowsCmdUtil;
import com.github.dmitriydb.etda.model.RegexConstraints;
import com.github.dmitriydb.etda.model.simplemodel.domain.*;
import com.github.dmitriydb.etda.resources.ResourceBundleManager;
import com.github.dmitriydb.etda.security.SecurityManager;
import com.github.dmitriydb.etda.security.SecurityRole;
import com.github.dmitriydb.etda.security.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.NoSuchElementException;


/**
 * Простое консольное представление для терминала Windows
 *
 * @version 0.1.1
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
                if (!processSingleCharacter()) break;
            }
        }
    }

    /**
     * считывает символ с терминала и делегирует выполнение команды, привязанной к этому символу,
     * методу processCommand
     * <p>
     * если возникает исключение NoSuchElementException, то возвращает false
     * <p>
     * Используется в ответ на ввод пользователя на экране со списком данных
     *
     * @since 0.1
     */
    private boolean processSingleCharacter() {
        try {
            String s = in.nextLine();
            if (!s.trim().equals(""))
                processCommand(s);
        } catch (NoSuchElementException ex) {
            return false;
        }
        return true;
    }

    /**
     * Обрабатывает первый символ строки и выполняет команду, привязанную к этому символу
     * <p>
     * Используется в ответ на ввод пользователя на экране со списком данных
     *
     * @param s
     * @since 0.1
     */
    private void processCommand(String s) {
        logger.debug("Current option = {}", this.currentOption);
        char c = s.charAt(0);

        if (c == 'c') {
            this.setOffset(0);
            processInput();
        } else if (this.currentOption.getActionType().equals(ConsoleActionType.SCROLLABLE)) {
            switch (c) {
                case '+': {
                    this.setOffset(getOffset() + 1);
                    ConsoleViewRequest request = new ConsoleViewRequest(currentOption, getOffset());
                    request.setFilter(filter);
                    processUserAction(request);
                    break;
                }
                case '-': {
                    this.setOffset(getOffset() - 1);
                    ConsoleViewRequest request = new ConsoleViewRequest(currentOption, getOffset());
                    request.setFilter(filter);
                    processUserAction(request);
                    break;
                }
                case 'n': {
                    isProcessLastRequest = true;
                    createEntity(ConsoleViewOptions.CREATE_EMPLOYEE);
                    break;
                }
                case 'd': {
                    isProcessLastRequest = true;
                    deleteEntity(ConsoleViewOptions.DELETE_EMPLOYEE);
                    break;
                }
                case 'u': {
                    isProcessLastRequest = true;
                    updateEntity(ConsoleViewOptions.UPDATE_EMPLOYEE);
                    break;
                }
                default: {
                    setOffset(0);
                    filter = s;
                    ConsoleViewRequest request = new ConsoleViewRequest(currentOption, getOffset());
                    request.setFilter(filter);
                    processUserAction(request);
                }
            }
        }
    }

    /**
     * Перечисляет все доступные опции меню
     *
     * Теперь работает с любыми списками класса OptionsSet
     *
     * @since 0.1.1
     */
    private void listOptions() {
        Integer seqNumber = 1;
        for (ConsoleViewOptions option : this.getOptions().options) {
            String optionName = line(option.getOptionLabel());
            String op = String.format("%d) %s", seqNumber++, line(resourceBundle.getString(optionName)));
            out.println(op);
        }
    }

    private boolean isDateValid(String date) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
            LocalDate d = LocalDate.parse(date, formatter);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * Запрашивает дату у пользователя, пока он не введет корректную в региональном формате
     * Либо если он нажмет специальную клавишу выхода - вернет в меню
     *
     * @return дата типа java.sql.date
     * @since 0.1
     */
    private Date inputDate() {
        String sDate = in.nextLine();
        while (!isDateValid(sDate)) {
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
     *
     * @return объект типа Employee
     * @since 0.1
     */
    private Employee inputEmployee() {
        Employee e = new Employee();
        out.println(l("EnterName"));
        String name = in.nextLine();
        while (!RegexConstraints.match(name, RegexConstraints.VALID_NAME_PATTERN)) {
            out.println(l("WrongName"));
            name = in.nextLine();
        }

        e.setFirstName(name);
        out.println(l("EnterSurname"));
        String surname = in.nextLine();
        while (!RegexConstraints.match(surname, RegexConstraints.VALID_SURNAME_PATTERN)) {
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

    private Department updateDepartment(Department original) {
        out.println(l("EnterDepartmentName") + encase(original.getName()));
        String name = in.nextLine();
        while (!RegexConstraints.match(name, RegexConstraints.VALID_DEPARTMENT_NAME)) {
            out.println(l("WrongName"));
            name = in.nextLine();
        }
        original.setName(name);
        return original;
    }

    private Department inputDepartment() {
        Department e = new Department();
        out.println(l("EnterDepartmentId"));
        String code = in.nextLine();
        while (!RegexConstraints.match(code, RegexConstraints.VALID_DEPARTMENT_NUMBER)) {
            out.println(l("WrongCode"));
            code = in.nextLine();
        }
        e.setDepartmentId(code);
        out.println(l("EnterDepartmentName"));
        String name = in.nextLine();
        while (!RegexConstraints.match(name, RegexConstraints.VALID_DEPARTMENT_NAME)) {
            out.println(l("WrongName"));
            name = in.nextLine();
        }
        e.setName(name);
        return e;
    }

    /**
     * Метод, который просит пользователя ввести строку до тех пор, пока она не будет проходить валидацию
     * Если метод возвращает null, то это означает конец ввода
     *
     * @param pattern паттерн валидации
     * @param message сообщение, которое будет выводиться при непрошедшей валидации
     * @since 0.1
     */
    private String getValidString(String pattern, String message) {
        String s = in.nextLine();
        while (!RegexConstraints.match(s, pattern)) {
            System.out.println(message);
            s = in.nextLine();
        }
        return s;
    }

    private Title inputTitle() {
        Title t = new Title();
        TitleOrder order = new TitleOrder();
        Long id = getID();
        order.setEmployeeNumber(id);
        out.println(l("EnterTitle"));
        String title = getValidString(RegexConstraints.VALID_TITLE_NAME, l("WrongTitle"));
        order.setTitle(title);
        out.println(l("EnterStartDate"));
        Date startDate = inputDate();
        out.println(l("EnterEndDate"));
        Date endDate = inputDate();
        order.setFromDate(startDate);
        t.setTitleOrder(order);
        t.setToDate(endDate);
        return t;
    }

    private Salary inputSalary() {
        Salary s = new Salary();
        SalaryOrder order = new SalaryOrder();
        Long id = getID();
        order.setEmployeeNumber(id);
        out.println(l("EnterSalaryAmount"));
        String sAmount = getValidString(RegexConstraints.VALID_SALARY_AMOUNT_PATTERN, l("WrongAmount"));
        Long salary = Long.valueOf(sAmount);
        s.setSalary(salary);
        out.println(l("EnterStartDate"));
        Date startDate = inputDate();
        out.println(l("EnterEndDate"));
        Date endDate = inputDate();
        order.setFromDate(startDate);
        s.setToDate(endDate);
        s.setSalaryOrder(order);
        return s;
    }

    private DepartmentEmployee inputDepartmentEmployee() {
        DepartmentEmployee e = new DepartmentEmployee();
        DepartmentEmployeeSuite suite = new DepartmentEmployeeSuite();
        Long id = getID();
        suite.setEmployeeNumber(id);
        out.println(l("EnterDepartmentId"));
        String departmentCode = getValidString(RegexConstraints.VALID_DEPARTMENT_NUMBER, l("WrongCode"));
        suite.setDepartmentId(departmentCode);
        out.println(l("EnterStartDate"));
        Date startDate = inputDate();
        out.println(l("EnterEndDate"));
        Date endDate = inputDate();
        e.setFromDate(startDate);
        e.setToDate(endDate);
        e.setDepartmentEmployeeSuite(suite);
        return e;
    }

    private DepartmentManager inputManager() {
        DepartmentManager manager = new DepartmentManager();
        DepartmentEmployeeSuite suite = new DepartmentEmployeeSuite();
        Long id = getID();
        suite.setEmployeeNumber(id);
        out.println(l("EnterDepartmentId"));
        String departmentCode = getValidString(RegexConstraints.VALID_DEPARTMENT_NUMBER, l("WrongCode"));
        suite.setDepartmentId(departmentCode);
        out.println(l("EnterStartDate"));
        Date startDate = inputDate();
        out.println(l("EnterEndDate"));
        Date endDate = inputDate();
        manager.setFromDate(startDate);
        manager.setToDate(endDate);
        manager.setDepartmentManagerSuite(suite);
        return manager;
    }

    /**
     * Получает строку из текстовых ресурсов по лейблу
     *
     * @param label
     * @return
     * @since 0.1
     */
    private String l(String label) {
        return line(resourceBundle.getString(label));
    }

    /**
     * Получает и возвращает введенный пользователем ID записи
     *
     * @return корректное число типа Long
     * @since 0.1
     */
    private Long getID() {
        out.println(l("EnterLineNumber"));
        String line = in.nextLine();
        Long id = Long.valueOf(line);
        return id;
    }

    private void createEntity(ConsoleViewOptions option) {
        Object e = inputEntity(option);
        if (e != null) {
            ConsoleViewRequest request = new ConsoleViewRequest(option, 0);
            request.setBean(e);
            processUserAction(request);
        }
    }

    private Object inputEntity(ConsoleViewOptions option) {
        if (option == ConsoleViewOptions.CREATE_EMPLOYEE)
            return inputEmployee();
        if (option == ConsoleViewOptions.CREATE_DEPARTMENT)
            return inputDepartment();
        if (option == ConsoleViewOptions.CREATE_TITLE)
            return inputTitle();
        if (option == ConsoleViewOptions.CREATE_SALARY)
            return inputSalary();
        if (option == ConsoleViewOptions.CREATE_DEPT_EMP)
            return inputDepartmentEmployee();
        if (option == ConsoleViewOptions.CREATE_DEPT_MANAGER)
            return inputManager();
        return null;
    }

    private Object updateEntity(Object original) {
        if (original instanceof Employee) return updateEmployee((Employee) original);
        if (original instanceof Department) return updateDepartment((Department) original);
        else return original;
    }

    private void deleteEntity(ConsoleViewOptions option) {
        Long id = getID();
        ConsoleViewRequest request = new ConsoleViewRequest(option, 0);
        request.setId(id);
        processUserAction(request);
    }

    private void updateEntity(ConsoleViewOptions option) {
        Serializable id;
        if (!option.equals(ConsoleViewOptions.UPDATE_DEPARTMENT))
            id = getID();
        else {
            System.out.println(l("EnterDepartmentId"));
            id = getValidString(RegexConstraints.VALID_DEPARTMENT_NUMBER, l("WrongCode"));
        }

        Object result = controller.getEntity(option.getEntityClass(), id);
        if (result == null) {
            out.println(l("IdNotExists"));
            processInput();
        }
        Object e = result;
        e = updateEntity(e);
        ConsoleViewRequest request = new ConsoleViewRequest(option, 0);
        request.setBean(e);
        processUserAction(request);
    }

    private Employee updateEmployee(Employee original) {
        out.println(l("EnterName") + encase(original.getFirstName()));
        String name = in.nextLine();
        while (!RegexConstraints.match(name, RegexConstraints.VALID_NAME_PATTERN)) {
            out.println(l("WrongName"));
            name = in.nextLine();
        }
        original.setFirstName(name);
        out.println(l("EnterSurname") + encase(original.getLastName()));
        String surname = in.nextLine();
        while (!RegexConstraints.match(surname, RegexConstraints.VALID_SURNAME_PATTERN)) {
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


    /**
     * Метод запрашивает у пользователя строку до тех пор, пока она не будет соответствовать паттерну
     *
     * @param initialMessageLabel лейбл сообщения, которое показывается в начале ввода
     * @param wrongMessageLabel лейбл сообщения, которое будет показываться после каждой ошибки
     * @param pattern регулярное выражение для проверки
     * @return
     */
    private String getValidString(String initialMessageLabel, String wrongMessageLabel, String pattern){
        out.println(l(initialMessageLabel));
        String s = in.nextLine();
        while (!RegexConstraints.match(s, pattern)) {
            out.println(l(wrongMessageLabel));
            s = in.nextLine();
        }
        return s;
    }

    /**
     * Запрашивает у пользователя данные для регистрации и возвращает объект класса User
     * По умолчанию роль пользователя устанавливается в Employee
     * @return
     *
     * @since 0.1.1
     */
    private User registerUser(){
        User u = new User();
        u.setSecurityRole(SecurityRole.EMPLOYEE_ROLE());
        String username = getValidString("EnterUsername", "WrongUsername", RegexConstraints.VALID_USERNAME);
        String password = "";
        String passwordAgain = "";
        do{
            if (!password.equals(passwordAgain)){
                System.out.println(l("PasswordsDoNotMatch"));
            }
            password = getValidString("EnterPassword", "WrongPassword", RegexConstraints.VALID_PASSWORD);
            passwordAgain = getValidString("EnterPasswordAgain", "WrongPassword", RegexConstraints.VALID_PASSWORD);
        }
        while (!password.equals(passwordAgain));
        passwordAgain = null;
        password = SecurityManager.hashPassword(password);
        System.out.println(l("EnterEmail"));
        String email = in.nextLine();
        while (!email.equals("") || RegexConstraints.match(email, RegexConstraints.VALID_EMAIL)){
            System.out.println(l("WrongEmail"));
            email = in.nextLine();
        }
        u.setName(username);
        u.setPassword(password);
        u.setEmail(email);
        return u;
    }


    /**
     * Метод запрашивает у пользователя логин и пароль
     * Затем отправляет контроллеру данные на авторизацию
     * @return пустой объект User с заполненными полями name и password
     */
    private User loginUser(){
        User u = new User();
        String username = getValidString("EnterUsername", "WrongUsername", RegexConstraints.VALID_USERNAME);
        String password = getValidString("EnterPassword", "WrongPassword", RegexConstraints.VALID_PASSWORD);
        u.setName(username);
        u.setPassword(password);
        return u;
    }

    /**
     * Делегирует обработку запроса контроллеру
     *
     * @param request
     * @since 0.1
     */
    private void processUserAction(ConsoleViewRequest request) {
        controller.processUserAction(request);
    }

    /**
     * Обрабатывает выбор пользователем пункта основного меню
     *
     * Теперь должен учитывать номер опции в конкретном наборе опций класса OptionsSet
     *
     * @since 0.1.1
     */
    @Override
    protected void processInput() {
        filter = "";
        out.println(line(resourceBundle.getString("action")));
        listOptions();
        this.changeState(ViewState.WAITING_USER_INPUT);
        try {
            String line = in.nextLine();
            Integer i = Integer.valueOf(line);
            ConsoleViewOptions option = getOptions().options.get(i - 1);
            switch (option.getActionType()) {
                case CREATE: {
                    createEntity(option);
                    break;
                }
                case DELETE: {
                    deleteEntity(option);
                    break;
                }
                case UPDATE: {
                    updateEntity(option);
                    break;
                }
                case USER_OPERATION:{
                    switch (option){
                        case LOGIN:
                        {
                            User u = loginUser();
                            ConsoleViewRequest request = new ConsoleViewRequest(ConsoleViewOptions.LOGIN);
                            request.setBean(u);
                            controller.processUserAction(request);
                            break;
                        }
                        case REGISTER:{
                            User u = registerUser();
                            ConsoleViewRequest request = new ConsoleViewRequest(ConsoleViewOptions.REGISTER);
                            request.setBean(u);
                            controller.processUserAction(request);
                            break;
                        }
                        case EXIT: {
                            System.out.println(l("Bye"));
                            System.exit(0);
                            break;
                        }
                    }
                }
                default: {
                    this.changeState(ViewState.WAITING_CONTROLLER);
                    ConsoleViewRequest request = new ConsoleViewRequest(option, 0);
                    lastRequest = request;
                    processUserAction(request);
                    break;
                }
            }
        } catch (NoSuchElementException ex) {

        }
        catch (Exception ex) {
            logger.error("Error", ex);
            out.println(l("WrongMenuOption"));
            processInput();
        }
    }

    private String encase(String s) {
        return "[" + s + "]";
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
        if (isProcessLastRequest && lastRequest != null) {
            logger.debug("last request = {}, isProcess = {}", lastRequest.toString(), isProcessLastRequest);
            isProcessLastRequest = false;
            this.processUserAction(lastRequest);
        }
        updateSelf();
    }
}
