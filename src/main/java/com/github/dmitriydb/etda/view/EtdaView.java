package com.github.dmitriydb.etda.view;

import com.github.dmitriydb.etda.controller.EtdaController;
import com.github.dmitriydb.etda.resources.ResourceBundleManager;
import com.github.dmitriydb.etda.security.User;
import com.github.dmitriydb.etda.view.console.ConsoleViewOptions;
import com.github.dmitriydb.etda.view.console.ViewState;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Абстрактный класс представления для приложения
 *
 * @version 0.1.2
 * @since 0.1
 */
public abstract class EtdaView {

    /**
     * Пользователь, работающий с данным представлением в текущий момент
     */
    private User user;

    /**
     * Комплект ресурсов, загруженный в представление
     */
    protected ResourceBundle resourceBundle;

    /**
     * Текущее состояние представления
     */
    protected ViewState currentState = ViewState.CREATED;
    protected EtdaController controller;

    /**
     * Максимальное количество результатов выборки на 1 странице
     */
    protected int maxResults = 20;

    /**
     * Текущее действие пользователя
     * Используется для индикации контекста, например во время активного действия SHOW_LIST предполагается возможность прокручивать список, выбирать элементы и т.д
     */
    protected ConsoleViewOptions currentOption;

    /**
     * Смещение результатов поиска на текущем экране
     * 0, если результаты отображаются с 1 позиции
     */
    private int offset = 0;

    /**
     * метод вызывается, чтобы обновить состояние представления
     */
    public abstract void updateSelf();

    public void setController(EtdaController controller){
        this.controller = controller;
    }

    public void changeState(ViewState newState){
        this.currentState = newState;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
        if (this.offset < 0) this.offset = 0;
    }

    public int getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(int maxResults) {
        this.maxResults = maxResults;
    }

    public ConsoleViewOptions getCurrentOption() {
        return currentOption;
    }

    public void setCurrentOption(ConsoleViewOptions currentOption) {
        this.currentOption = currentOption;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        this.resourceBundle = ResourceBundleManager.getConsoleResourceBundle(getUser().getLocale());
    }

    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    public void setResourceBundle(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    public ViewState getCurrentState() {
        return currentState;
    }
}
