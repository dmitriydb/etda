package com.github.dmitriydb.etda.view.console;

import java.io.Serializable;

public class ConsoleViewRequest extends ViewRequest{

    private Object bean;

    private final String requestMessage;

    private final int offset;

    private String filter;

    private Serializable id;

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

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }

    @Override
    public String toString() {
        return "ConsoleViewRequest{" +
                "requestMessage='" + requestMessage + '\'' +
                ", offset=" + offset +
                '}';
    }

    public Serializable getId() {
        return id;
    }

    public void setId(Serializable id) {
        this.id = id;
    }
}
