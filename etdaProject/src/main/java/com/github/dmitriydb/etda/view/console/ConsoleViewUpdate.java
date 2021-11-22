package com.github.dmitriydb.etda.view.console;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс, представляющий собой сообщение, посылаемое из контроллера в представление с целью обновления
 *
 * В данный момент на любую операцию в ответ отправляется список объектов класса Object,
 * в дальнейшем нужно будет перевести общение между компонентами на конкретный протокол
 *
 * @version 0.1
 * @since 0.1
 */
public class ConsoleViewUpdate {

    private List<Object> messages = new ArrayList<>();


    public void addMessage(Object message){
        this.messages.add(message);
    }

    public List<Object> getMessages() {
        return messages;
    }

    @Override
    public String toString() {
        return "ConsoleViewUpdate{" +
                "messages=" + messages +
                '}';
    }
}
