package com.github.dmitriydb.etda.view.console;

import com.github.dmitriydb.etda.consoleapp.WindowsCmdUtil;
import com.github.dmitriydb.etda.controller.console.ConsoleController;
import com.github.dmitriydb.etda.dbutils.DbManager;
import com.github.dmitriydb.etda.model.simplemodel.domain.Employee;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты пользовательского интерфейса для консольного приложения
 * ТЕСТИРУЕТСЯ РУССКИЙ ИНТЕРФЕЙС!
 *
 * @version 0.1
 * @since 0.1
 */
class SimpleConsoleViewTest {

    private ConsoleController controller;
    private ConsoleView view;
    private Employee e;

    /**
     * В данный момент тестируется на формате даты из российского региона
     */
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy");

    /**
     * Нужно инициализировать фабрику сессий перед всеми тестами
     */
    @BeforeAll
    public static void initController() {
        DbManager.init();
    }

    /**
     * Перед каждым тестом создаются новые контроллер и модель
     * И сотрудник со случайными данными
     *
     * @since 0.1
     */
    @BeforeEach
    public void initControllerAndView() {
        controller = new ConsoleController();
        view = (ConsoleView) controller.getView();
        e = new Employee();
        e.setGender(getGenderByDigit(getRandomGender()));
        e.setFirstName(generateRandomName());
        e.setLastName(generateRandomName());
        e.setHireDate(getRandomDate());
        e.setBirth_date(getRandomDate());
    }

    /**
     * Конвертирует строку "1" в "M", иначе возвращает "F"
     * Метод нужен для ввода пола сотрудника в пользовательском интерфейсе
     *
     * @since 0.1
     * @param s
     * @return
     */
    private char getGenderByDigit(String s){
        if (s.equals("1")) return 'M';
        else
        return 'F';
    }

    public void refreshControllerAndView(){
        controller = new ConsoleController();
        view = (ConsoleView) controller.getView();
    }

    /**
     * Если строка-аргумент равна "M" - возвращает "1"
     * Иначе возвращает "2"
     * Метод нужен для ввода пола сотрудника в пользовательском интерфейсе
     * @param c
     * @return
     *
     * @since 0.1
     */
    public String getDigitByGender(char c){
        if (c == 'M') return "1";
        else return "2";
    }

    /**
     * Конвертирует данные общего сотрудника для тестирования в строку для ввода в пользовательском интерфейсе
     * @return
     * @since 0.1
     */
    private String prepareEmployeeString(){
        StringBuilder employee = new StringBuilder();
        employee.append(e.getFirstName() + "\n");
        employee.append(e.getLastName() + "\n");
        employee.append(sqlDateToStringDate(e.getBirth_date()) + "\n");
        employee.append(sqlDateToStringDate(e.getHireDate()) + "\n");
        employee.append(getDigitByGender(e.getGender()) + "\n");
        return employee.toString();
    }

    /**
     * Конвертирует данные конкретного сотрудника (передается как параметр) для тестирования в строку для ввода в пользовательском интерфейсе
     * @param e
     * @return
     *
     * @since 0.1
     */
    private String prepareEmployeeString(Employee e){
        StringBuilder employee = new StringBuilder();
        employee.append(e.getFirstName() + "\n");
        employee.append(e.getLastName() + "\n");
        employee.append(sqlDateToStringDate(e.getBirth_date()) + "\n");
        employee.append(sqlDateToStringDate(e.getHireDate()) + "\n");
        employee.append(getDigitByGender(e.getGender()) + "\n");
        return employee.toString();
    }

    /**
     * Создает объект Employee и наполняет случайными данными для тестирования
     *
     * @since 0.1
     * @return
     */
    private Employee generateRandomEmployee(){
        Employee e = new Employee();
        e.setGender(getGenderByDigit(getRandomGender()));
        e.setFirstName(generateRandomName());
        e.setLastName(generateRandomName());
        e.setHireDate(getRandomDate());
        e.setBirth_date(getRandomDate());
        return e;
    }

    /**
     * Заставляет представление выполнить команды и возвращает результат работы приложения в виде массива строк
     * @param input команды в строке
     * @return
     *
     * @since 0.1
     */
    private String[] makeViewProcessInput(String input) {
        view.setIn(new Scanner(input));
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final String utf8 = StandardCharsets.UTF_8.name();
        try (PrintStream ps = new PrintStream(baos, true, utf8)) {
            view.setOut(ps);
            controller.startWork();
            return baos.toString(utf8).split("\\n");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Test
    public void employeesListIsWorkingOkay() {
        try {
            String[] result = makeViewProcessInput("1\n");
            assertEquals(result.length, 44);
        } catch (NoSuchElementException ex) {

        }
    }

    /**
     * Выводит результат работы приложения с пронумерованными строками
     * @param result
     *
     * @since 0.1
     */
    public void formattedOutput(String[] result){
        for (int i = 0; i < result.length; i++){
            System.out.println(i + " : " + result[i]);
        }
    }


    @Test
    public void creatingEmployeeThenFilterByName(){
            String name = e.getFirstName();
            String command = "9\n" + prepareEmployeeString() + "1\n" + name + "\n";
            String[] result = makeViewProcessInput(command);
            for (int i = result.length - 1; i >= 0; i--){
                if (result[i].trim().startsWith("Current filter")) continue;
                System.out.println(e.getFirstName() + " in " + result[i]);
                assertTrue(result[i].contains(e.getFirstName()));
                break;
            }
    }

    /**
     * Вытаскивает номер из строки
     * В общем виде достает первый элемент из строки, разделенной пробелами
     * @param line
     * @return
     *
     * @since 0.1
     */
    private String getIdFromLine(String line){
        String[] splits = line.trim().split(" ");
        return splits[0].trim();
    }

    @Test
    public void creatingEmployeeThenFilterBySurname(){
        String surname = e.getLastName();
        String command = "9\n" + prepareEmployeeString() + "1\n" + surname + "\n";
        String[] result = makeViewProcessInput(command);
        for (int i = result.length - 1; i >= 0; i--){
            if (result[i].trim().startsWith("Current filter")) continue;
            assertTrue(result[i].contains(e.getLastName()));
            break;
        }

    }

    @Test
    public void creatingEmployeeThenFilterByEmpNo(){
        String surname = e.getLastName();
        String name = e.getFirstName();
        String command = "9\n" + prepareEmployeeString() + "1\n" + e.getLastName() + "\n";
        String[] result = makeViewProcessInput(command);
        String empNo = getIdFromLine(result[result.length - 3]);
        refreshControllerAndView();
        command = "1\n" + empNo + "\n";
        result = makeViewProcessInput(command);
        assertTrue(result[result.length - 3].contains(name) && result[result.length - 3].contains(surname));
    }

    @Test
    public void scrollingIsWorkingOkay(){
        String command = "1\n";
        String[] result = makeViewProcessInput(command);
        List<String> initial = getLinesFromOutput(result);
        refreshControllerAndView();
        List<String> initialPlusOne = getLinesFromOutput(makeViewProcessInput("1\n+\n"));
        refreshControllerAndView();
        List<String> initialPlusTwo = getLinesFromOutput(makeViewProcessInput("1\n+\n+\n"));
        refreshControllerAndView();
        List<String> afterPlusTwoAndMinusOne = getLinesFromOutput(makeViewProcessInput("1\n+\n+\n-\n"));
        assertEquals(afterPlusTwoAndMinusOne, initialPlusOne);
        refreshControllerAndView();
        List<String> afterPlusTwoAndMinusTwo = getLinesFromOutput(makeViewProcessInput("1\n+\n+\n-\n-\n"));
        assertEquals(afterPlusTwoAndMinusTwo, initial);
    }

    @Test
    public void creatingEmployeeFromInnerMenu(){
        String name = e.getFirstName();
        String command = "1\nn\n" + prepareEmployeeString() + "\n" + name + "\n";
        String[] result = makeViewProcessInput(command);
        for (int i = result.length - 1; i >= 0; i--){
            if (result[i].trim().startsWith("Current filter")) continue;
            assertTrue(result[i].contains(e.getFirstName()) && result[i].contains(e.getLastName()));
            break;
        }
    }

    @Test
    public void creatingEmployeeFromInnerMenuAndCheckingAllFields(){
        String name = e.getFirstName();
        String k = prepareEmployeeString();
        String command = "1\nn\n" + k + "\n" + name + "\n";
        String[] result = makeViewProcessInput(command);
        String line = result[result.length - 5];
        assertTrue(line.contains(e.getLastName()) && line.contains(e.getFirstName())
        && line.contains(e.getGender().toString()) && line.contains(e.getBirth_date().toString()) && line.contains(e.getHireDate().toString()));
    }

    @Test
    public void creatingAndEditingEmployee(){
        String command = "9\n" + prepareEmployeeString() + "1\n" + e.getLastName() + "\n";
        String[] result = makeViewProcessInput(command);
        String empNo = getIdFromLine(result[result.length - 3]);
        refreshControllerAndView();
        Employee newData = generateRandomEmployee();
        String newDataInput = prepareEmployeeString(newData);
        command = "1\nu\n"+empNo+"\n"+newDataInput+empNo + "\n";
        result = makeViewProcessInput(command);
        String line = result[result.length - 5];
        assertTrue(line.contains(newData.getFirstName()) &&
                line.contains(newData.getLastName()) && line.contains(empNo));
        HashMap<String, Boolean> oldValues = new HashMap<>();
        assertTrue(Arrays.toString(result).contains("[" + e.getLastName() + "]"));
        assertTrue(Arrays.toString(result).contains("[" + e.getFirstName() + "]"));
        assertTrue(Arrays.toString(result).contains("[" + getDigitByGender(e.getGender()) + "]"));
        assertTrue(Arrays.toString(result).contains("[" + e.getHireDate().toString() + "]"));
        assertTrue(Arrays.toString(result).contains("[" + e.getBirth_date().toString() + "]"));
    }

    /**
     * Достает последние 20 строк из вывода и возвращает в виде списка строк
     * @param lines
     * @return
     *
     * @since 0.1
     */
    private List<String> getLinesFromOutput(String[] lines) {
        ArrayList<String> result = new ArrayList<>();
        for (int i = 0; i < 20; i++){
                result.add(lines[lines.length - 1 - i]);
        }
        return result;
    }

    /**
     * Метод генерирует случайное имя сотрудника для тестирования
     * Длина имени от 5 до 13 символов
     * Имя содержит русские и английские буквы в обоих регистрах
     *
     * @return
     * @since 0.1
     */
    private String generateRandomName() {
          int nameLen = ThreadLocalRandom.current().nextInt(5, 13);
        StringBuilder result = new StringBuilder("");
        for (int i = 0; i < nameLen; i++) {
            result.append(generateRandomLetter());
        }
        return result.toString();
    }

    /**
     * Метод возвращает случайную букву из кириллического или латинского алфавита ( в обоих регистрах )
     *
     * @return
     * @since 0.1
     */
    private char generateRandomLetter() {
        int listChoose = ThreadLocalRandom.current().nextInt(1, 4);
        switch (listChoose) {
            case 1:
                return (char) ('a' + ThreadLocalRandom.current().nextInt(0, 'z' - 'a' - 1));
            case 2:
                return (char) ('A' + ThreadLocalRandom.current().nextInt(0, 'Z' - 'A' - 1));
            case 3:
                return (char) ('А' + ThreadLocalRandom.current().nextInt(0, 'Я' - 'А' - 1));
            case 4:
                return (char) ('а' + ThreadLocalRandom.current().nextInt(0, 'я' - 'а' - 1));
        }
        return '0';
    }

    /**
     * Случайным образом возвращает 1 или 2
     * @return
     *
     * @since 0.1
     */
    private String getRandomGender() {
        int x = ThreadLocalRandom.current().nextInt(1, 3);
        return Integer.toString(x);
    }

    /**
     * Генерирует случайную дату типа java.sql.Date для тестирования
     * @return
     */
    public Date getRandomDate() {
        long randomDay = ThreadLocalRandom
                .current()
                .nextLong(0, 10000);
        LocalDate date = LocalDate.now().plus(randomDay, ChronoUnit.DAYS);
        return Date.valueOf(date);
    }

    /**
     * Конвертирует дату в формате java.sql.Date в строку с форматом, поддерживаемым русским интерфейсом
     * @param date
     * @return
     *
     * @since 0.1
     */
    public String sqlDateToStringDate(Date date){
        LocalDate d = date.toLocalDate();
        return formatter.format(d);
    }

    /**
     * генерирует код департамента вида dXXX
     * XXX варьируется от 100 до 999
     * @return
     *
     * @since 0.1
     */
    public String generateDepartmentCode(){
        StringBuilder result = new StringBuilder();
        result.append("d");
        result.append(Integer.toString(ThreadLocalRandom.current().nextInt(100, 1000)));
        return result.toString();
    }

}