package com.github.dmitriydb.etda.model;

public class RegexConstraints {
    public static final String VALID_NAME_PATTERN = "^[а-яА-Яa-zA-Z'\\s-]{1,30}$";
    public static final String VALID_SURNAME_PATTERN = "^[а-яА-Яa-zA-Z'\\s-]{1,30}$";
    public static final String VALID_DEPARTMENT_NUMBER = "^d[\\d]{3}$";
    public static final String VALID_DEPARTMENT_NAME = "[a-zA-Zа-яА-Я\\s]{1,40}";
    public static final String VALID_TITLE_NAME = "[a-zА-ЯA-Zа-я\\s]{1,50}";
    public static final String VALID_SALARY_AMOUNT_PATTERN = "[\\d]{1,10}";

    public static boolean match(String s, String pattern){
        return s.matches(pattern);
    }
}
