package com.github.dmitriydb.etda.consoleapp;


import org.junit.Test;

public class WindowsCmdUtilTest {

    @Test(expected = IllegalArgumentException.class)
    public void throwException(){
        WindowsCmdUtil.convertToTerminalString(null);
    }
}