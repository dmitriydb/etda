package com.github.dmitriydb.etda.view.console;

public class ConsoleViewRequest extends ViewRequest{
    private final String requestMessage;

    private final int offset;

    private String filter;

    public ConsoleViewRequest(String requestMessage, int offset) {
        this.requestMessage = requestMessage;
        this.offset = offset;
    }

    public String getRequestMessage() {
        return requestMessage;
    }

    public int getOffset() {
        return offset;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    @Override
    public String toString() {
        return "ConsoleViewRequest{" +
                "requestMessage='" + requestMessage + '\'' +
                ", offset=" + offset +
                '}';
    }


}
