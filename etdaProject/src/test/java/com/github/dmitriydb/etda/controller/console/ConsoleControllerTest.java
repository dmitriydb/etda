package com.github.dmitriydb.etda.controller.console;

import com.github.dmitriydb.etda.dbutils.DbManager;
import com.github.dmitriydb.etda.model.EtdaModel;
import com.github.dmitriydb.etda.model.SimpleModel;
import com.github.dmitriydb.etda.security.User;
import com.github.dmitriydb.etda.view.console.*;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ConsoleControllerTest {


    @Test
    public void controllerAddingOptionsToViewAfterCreating(){
        SimpleModel model = Mockito.mock(SimpleModel.class);
        SimpleConsoleView view = Mockito.mock(SimpleConsoleView.class);

        ConsoleController controller = new ConsoleController(model, view);

        verify(view, times(1)).setOptions(new StartMenuOptionsSet());
    }

    @Test
    public void controllerUpdatingViewAfterStartingWork(){
        SimpleConsoleView view = mock(SimpleConsoleView.class);
        SimpleModel model = Mockito.mock(SimpleModel.class);
        ConsoleController controller = new ConsoleController(model, view);
        controller.startWork();
        verify(view, times(1)).updateSelf();
    }

    @Test
    public void controllerIsCheckingPassword(){
        SimpleConsoleView view = mock(SimpleConsoleView.class);
        SimpleModel model = Mockito.mock(SimpleModel.class);
        ConsoleController controller = Mockito.spy(new ConsoleController(model, view));
        DbManager.init();
        controller.startWork();
        ConsoleViewOptions option = ConsoleViewOptions.LOGIN;
        ConsoleViewRequest request = new ConsoleViewRequest(option);
        User u = new User();
        u.setName("admin");
        u.setPassword("123");
        request.setBean(u);
        ConsoleViewUpdate update = new ConsoleViewUpdate();
        update.addMessage("InvalidPassword");

        controller.processUserAction(request);

        verify(view).processConsoleViewUpdate(update);
    }

}