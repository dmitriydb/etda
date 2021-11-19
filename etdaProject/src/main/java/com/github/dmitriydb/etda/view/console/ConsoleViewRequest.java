package com.github.dmitriydb.etda.view.console;

import java.io.Serializable;

/**
 * Класс, инкапсулирующий запрос к контроллеру из представления
 *
 * В будущем будет заменен на подходящий протокол
 * @version 0.1
 * @since 0.1
 */
public class ConsoleViewRequest extends ViewRequest{

    /**
     * Объект сущности, передаваемый в запросе
     * Нужен для обновления сущностей и подобных операций, когда необходимо передавать в запрос целый объект
     */
    private Object bean;

    /**
     * Сообщение
     * В данный момент передается номер операции, который равен порядковому номеру элемента перечисления ConsoleViewOptions + 1
     *
     * {@link com.github.dmitriydb.etda.view.console.ConsoleViewOptions}
     */
    private final String requestMessage;

    /**
     * смещение для возможности прокручивания выборки
     */
    private final int offset;

    /**
     * строка фильтра для фильтрации выборки
     */
    private String filter;

    /**
     * ID, по которому ищется элемент в запросе
     */
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
