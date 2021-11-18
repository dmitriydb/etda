package com.github.dmitriydb.etda.view.console;

import java.util.ArrayList;
import java.util.List;

public class ConsoleViewUpdate {

    private List<String> messages = new ArrayList<>();

    private int total;

    private int leftPosition;

    private int rightPosition;

    public void addMessage(String message){
        this.messages.add(message);
    }

    public List<String> getMessages() {
        return messages;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getLeftPosition() {
        return leftPosition;
    }

    public void setLeftPosition(int leftPosition) {
        this.leftPosition = leftPosition;
    }

    public int getRightPosition() {
        return rightPosition;
    }

    public void setRightPosition(int rightPosition) {
        this.rightPosition = rightPosition;
    }

    @Override
    public String toString() {
        return "ConsoleViewUpdate{" +
                "total=" + total +
                ", leftPosition=" + leftPosition +
                ", rightPosition=" + rightPosition +
                '}';
    }
}
