package com.github.dmitriydb.etda.view.console;

import java.util.ArrayList;
import java.util.List;

public class ConsoleViewUpdate {

    private List<String> messages = new ArrayList<>();

    public void addMessage(String message){
        this.messages.add(message);
    }

    public List<String> getMessages() {
        return messages;
    }
}
