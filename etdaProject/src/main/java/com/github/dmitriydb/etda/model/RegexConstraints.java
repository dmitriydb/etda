package com.github.dmitriydb.etda.model;

public class RegexConstraints {
    public static final String VALID_NAME_PATTERN = "^[а-яА-Яa-zA-Z'\\s-]{1,30}$";
    public static final String VALID_SURNAME_PATTERN = "^[а-яА-Яa-zA-Z'\\s-]{1,30}$";

    public static boolean match(String s, String pattern){
        return s.matches(pattern);
    }
}
