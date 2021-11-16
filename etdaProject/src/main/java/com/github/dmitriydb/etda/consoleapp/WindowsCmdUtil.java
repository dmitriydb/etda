package com.github.dmitriydb.etda.consoleapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

public class WindowsCmdUtil {

    private static Logger logger = LoggerFactory.getLogger(WindowsCmdUtil.class);

    public static String convertToTerminalString(String original){
        byte[] b = original.getBytes(StandardCharsets.ISO_8859_1);
        return new String(b);
    }
}
