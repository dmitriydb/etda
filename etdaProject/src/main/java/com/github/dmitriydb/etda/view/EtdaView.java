package com.github.dmitriydb.etda.view;

import com.github.dmitriydb.etda.controller.EtdaController;
import com.github.dmitriydb.etda.view.console.ConsoleViewOptions;
import com.github.dmitriydb.etda.view.console.ViewState;

import java.util.Locale;
import java.util.ResourceBundle;

public abstract class EtdaView {

    protected Locale locale = Locale.getDefault();
    protected ResourceBundle resourceBundle;
    protected ViewState currentState = ViewState.CREATED;
    protected EtdaController controller;
    protected int maxResults = 20;
    protected ConsoleViewOptions currentOption;

    /**
     * Смещение результатов поиска на текущем экране
     * 0, если результаты отображаются с 1 позиции
     */
    private int offset = 0;

    public abstract void updateSelf();

    public void setController(EtdaController controller){
        this.controller = controller;
    }

    public void changeLocale(Locale locale){
        this.locale = locale;
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
}
