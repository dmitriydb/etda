package com.github.dmitriydb.etda.view.console;

import com.github.dmitriydb.etda.consoleapp.WindowsCmdUtil;
import com.github.dmitriydb.etda.controller.EtdaController;
import com.github.dmitriydb.etda.resources.ResourceBundleManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.text.View;
import java.io.IOException;

public class SimpleConsoleView extends ConsoleView{

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
                System.out.println("Filter [" + filter + "]");
                processSingleCharacter();
            }
        }

    }

    private void processSingleCharacter(){
        String s = in.nextLine();
        char c = s.charAt(0);

        String option = String.valueOf(ConsoleViewOptions.EMPLOYEES_LIST.ordinal() + 1);
        if (this.currentOption.equals(ConsoleViewOptions.EMPLOYEES_LIST)){
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
            else if (c == 'c'){
               processInput();
            }
            else{
                filter = filter + c;
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
            System.out.format("%d) %s\n", seqNumber, optionName);
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
        for (String message : update.getMessages()){
            System.out.println(message);
        }
        updateSelf();
    }
}
