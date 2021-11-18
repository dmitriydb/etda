package com.github.dmitriydb.etda.view.console;

import com.github.dmitriydb.etda.consoleapp.WindowsCmdUtil;
import com.github.dmitriydb.etda.controller.EtdaController;
import com.github.dmitriydb.etda.resources.ResourceBundleManager;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.text.View;
import java.io.IOException;
import java.util.Scanner;

public class SimpleConsoleView extends ConsoleView{

    private Scanner in = new Scanner(System.in);


    private String filter = "";
    private static Logger logger = LoggerFactory.getLogger(SimpleConsoleView.class);

    public SimpleConsoleView(){
        this.resourceBundle = ResourceBundleManager.getConsoleResourceBundle();
    }

    @Override
    public void updateSelf() {
        if (this.currentState == ViewState.CREATED){
            System.out.println(line(resourceBundle.getString("welcome")));
            processInput();
        }
        else
        if (this.currentState == ViewState.WAITING_USER_INPUT || this.currentState == ViewState.WAITING_CONTROLLER){
            processInput();
        }
        else if (this.currentState == ViewState.WAITING_USER_KEY)
        {
            logger.debug("WAITING_USER_KEY");
            while (true){
                if (!filter.equals("")){
                    System.out.println("Current filter [" + filter + "]");
                }
              processSingleCharacter();
            }
        }
    }

    private void processSingleCharacter(){
        String s = in.nextLine();
        if (!s.trim().equals(""))
            processCommand(s);
    }

    private void processCommand(String s){
        logger.debug("Current option = {}", this.currentOption);
        char c = s.charAt(0);

        if (c == 'c'){
            this.setOffset(0);
            processInput();
        }
        else
        if (this.currentOption.getActionType().equals(ConsoleActionType.SCROLLABLE)){
            String option = String.valueOf(currentOption.ordinal() + 1);
            if (c == '+'){
                this.setOffset(getOffset() + 1);
                ConsoleViewRequest request = new ConsoleViewRequest(option, getOffset());
                request.setFilter(filter);
                controller.processUserAction(request);
            }
            else if (c == '-'){
                this.setOffset(getOffset() - 1);
                ConsoleViewRequest request = new ConsoleViewRequest(option, getOffset());
                request.setFilter(filter);
                controller.processUserAction(request);
            }
           else{
                setOffset(0);
                filter = s;
                ConsoleViewRequest request = new ConsoleViewRequest(option, getOffset());
                request.setFilter(filter);
                controller.processUserAction(request);

            }

        }
    }

    private void listOptions(){
        for (ConsoleViewOptions option : ConsoleViewOptions.values()){
            String optionName = line(option.getOptionLabel());
            Integer seqNumber = option.ordinal() + 1;
            String op = String.format("%d) %s", seqNumber, line(resourceBundle.getString(optionName)));
                System.out.println(op);
        }
    }

    @Override
    protected String line(String line) {
        return WindowsCmdUtil.convertToTerminalString(line);
    }

    @Override
    protected void processInput() {
        filter = "";
        System.out.println(line(resourceBundle.getString("action")));
        listOptions();
        this.changeState(ViewState.WAITING_USER_INPUT);
        String line = in.nextLine();
        this.changeState(ViewState.WAITING_CONTROLLER);
        controller.processUserAction(new ConsoleViewRequest(line, 0));
    }

    @Override
    public void processConsoleViewUpdate(ConsoleViewUpdate update) {
        logger.debug("Пришел апдейт {}", update);
        for (String message : update.getMessages()){
            System.out.println(message);
        }
        System.out.format("[Показаны %d - %d]\n", update.getLeftPosition(), update.getRightPosition());
        updateSelf();
    }
}
