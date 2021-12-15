package com.github.dmitriydb.etda.controller.console;

import com.github.dmitriydb.etda.dbutils.DbManager;
import com.github.dmitriydb.etda.model.EtdaModel;
import com.github.dmitriydb.etda.model.SimpleModel;
import com.github.dmitriydb.etda.security.User;
import com.github.dmitriydb.etda.view.console.*;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(JUnitParamsRunner.class)
public class ConsoleControllerTest {

    public Object[] scrollableOptionsToStateSet(){
        List<Object> result = new ArrayList<>();
        for (ConsoleViewOptions option : ConsoleViewOptions.values()){
            if (option.getActionType() != ConsoleActionType.SCROLLABLE) continue;
            Object[] arr = new Object[2];
            arr[0] = option;
            arr[1] = ViewState.WAITING_USER_KEY;
            result.add(arr);
        }
        return result.toArray();
    }

    public Object[] getTestOptionsByActionType(ConsoleActionType type){
        List<Object> result = new ArrayList<>();
        for (ConsoleViewOptions option : ConsoleViewOptions.values()){
            if (option.getActionType() != type) continue;
            result.add(option);
        }
        return result.toArray();
    }

    public Object[] createOptions(){
        return getTestOptionsByActionType(ConsoleActionType.CREATE);
    }

    public Object[] deleteOptions(){
        return getTestOptionsByActionType(ConsoleActionType.DELETE);
    }

    public Object[] updateOptions(){
        return getTestOptionsByActionType(ConsoleActionType.UPDATE);
    }

    @Test
    @Parameters(method = "createOptions")
    public void createOperationsWithNullBeanShowsCreateError(ConsoleViewOptions option){
        SimpleConsoleView view = mock(SimpleConsoleView.class);
        ConsoleController controller = Mockito.spy(new ConsoleController(new SimpleModel(), view));
        ConsoleViewRequest request = new ConsoleViewRequest(option);
        request.setBean(null);
        ArgumentCaptor<ConsoleViewUpdate> argument = ArgumentCaptor.forClass(ConsoleViewUpdate.class);
        controller.processUserAction(request);
        verify(view).processConsoleViewUpdate(argument.capture());
        ConsoleViewUpdate update = argument.getValue();
        assertThat(update.getMessages()).contains("CreateError");
        verify(view).changeState(ViewState.MENU);
    }

    @Test
    @Parameters(method = "updateOptions")
    public void updateOperationsWithNullBeanShowsUpdateError(ConsoleViewOptions option){
        SimpleConsoleView view = mock(SimpleConsoleView.class);
        ConsoleController controller = Mockito.spy(new ConsoleController(new SimpleModel(), view));
        ConsoleViewRequest request = new ConsoleViewRequest(option);
        request.setBean(null);
        ArgumentCaptor<ConsoleViewUpdate> argument = ArgumentCaptor.forClass(ConsoleViewUpdate.class);
        controller.processUserAction(request);
        verify(view).processConsoleViewUpdate(argument.capture());
        ConsoleViewUpdate update = argument.getValue();
        assertThat(update.getMessages()).contains("UpdateError");
        verify(view).changeState(ViewState.MENU);
    }

    @Test
    @Parameters(method = "deleteOptions")
    public void deleteOperationsWithMinusOneAsIdShowsDeleteError(ConsoleViewOptions option){
        SimpleConsoleView view = mock(SimpleConsoleView.class);
        ConsoleController controller = Mockito.spy(new ConsoleController(new SimpleModel(), view));
        ConsoleViewRequest request = new ConsoleViewRequest(option);
        request.setId(-1L);
        ArgumentCaptor<ConsoleViewUpdate> argument = ArgumentCaptor.forClass(ConsoleViewUpdate.class);
        controller.processUserAction(request);
        verify(view).processConsoleViewUpdate(argument.capture());
        ConsoleViewUpdate update = argument.getValue();
        assertThat(update.getMessages()).contains("IdNotExists");
        verify(view).changeState(ViewState.MENU);
    }

    @Test
    @Parameters(method = "scrollableOptionsToStateSet")
    public void testViewStatesAfterScrollableOperation(ConsoleViewOptions option, ViewState state){
        SimpleConsoleView view = mock(SimpleConsoleView.class);
        ConsoleController controller = Mockito.spy(new ConsoleController(new SimpleModel(), view));
        ConsoleViewRequest request = new ConsoleViewRequest(option);
        controller.processUserAction(request);
        verify(view).changeState(ViewState.WAITING_USER_KEY);
    }

    private User testEmployee;

    @Before
    public void setUp(){
        testEmployee = new User();
        testEmployee.setName("testemp");
        testEmployee.setPassword("12345678");
        DbManager.init();
    }

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
        ConsoleController controller = Mockito.spy(new ConsoleController(new SimpleModel(), view));
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
        //verify(view).processConsoleViewUpdate(update);
    }



    @Test
    public void whenProcessingUserActionThenSetViewCurrentOption(){
        SimpleConsoleView view = mock(SimpleConsoleView.class);
        SimpleModel model = Mockito.mock(SimpleModel.class);
        ConsoleController controller = Mockito.spy(new ConsoleController(model, view));
        ConsoleViewOptions option = ConsoleViewOptions.LOGIN;
        ConsoleViewRequest request = new ConsoleViewRequest(option);

        try{
            controller.processUserAction(request);
        }
        catch (Exception ex){
            verify(view).setCurrentOption( ConsoleViewOptions.LOGIN);
        }
    }

    @Test
    public void invalidLoginTest(){
        SimpleConsoleView view = mock(SimpleConsoleView.class);
        ConsoleController controller = Mockito.spy(new ConsoleController(new SimpleModel(), view));
        ConsoleViewOptions option = ConsoleViewOptions.LOGIN;
        ConsoleViewRequest request = new ConsoleViewRequest(option);
        User u = new User();
        u.setName("admin");
        u.setPassword("123");
        request.setBean(u);
        ArgumentCaptor<ConsoleViewUpdate> argument = ArgumentCaptor.forClass(ConsoleViewUpdate.class);
        controller.processUserAction(request);
        verify(view).processConsoleViewUpdate(argument.capture());
        ConsoleViewUpdate update = argument.getValue();
        assertThat(update.getMessages()).contains("InvalidPassword");
    }

    @Test
    public void sucLoginTest(){
        SimpleConsoleView view = mock(SimpleConsoleView.class);
        ConsoleController controller = Mockito.spy(new ConsoleController(new SimpleModel(), view));
        ConsoleViewOptions option = ConsoleViewOptions.LOGIN;
        ConsoleViewRequest request = new ConsoleViewRequest(option);
        request.setBean(testEmployee);
        ArgumentCaptor<ConsoleViewUpdate> argument = ArgumentCaptor.forClass(ConsoleViewUpdate.class);
        controller.processUserAction(request);
        verify(view).processConsoleViewUpdate(argument.capture());
        ConsoleViewUpdate update = argument.getValue();
        assertThat(update.getMessages()).contains("SucLogin");
    }

    @Test
    public void controllerIsSettingUserToViewAfterSuccessfulLogin(){
        SimpleConsoleView view = mock(SimpleConsoleView.class);
        ConsoleController controller = Mockito.spy(new ConsoleController(new SimpleModel(), view));
        ConsoleViewOptions option = ConsoleViewOptions.LOGIN;
        ConsoleViewRequest request = new ConsoleViewRequest(option);
        request.setBean(testEmployee);
        controller.processUserAction(request);
        verify(view).setUser(testEmployee);
    }

    @Test
    public void successfulAuthorizationLeadsToMenu(){
        SimpleConsoleView view = mock(SimpleConsoleView.class);
        ConsoleController controller = Mockito.spy(new ConsoleController(new SimpleModel(), view));
        ConsoleViewOptions option = ConsoleViewOptions.LOGIN;
        ConsoleViewRequest request = new ConsoleViewRequest(option);
        request.setBean(testEmployee);
        controller.processUserAction(request);
        verify(view).changeState(ViewState.MENU);
    }

    @Test
    public void nonexistingNameLeadsToUserNotExists(){
        SimpleConsoleView view = mock(SimpleConsoleView.class);
        ConsoleController controller = Mockito.spy(new ConsoleController(new SimpleModel(), view));
        ConsoleViewOptions option = ConsoleViewOptions.LOGIN;
        ConsoleViewRequest request = new ConsoleViewRequest(option);
        User u = new User();
        u.setName("abcadfdfd");
        request.setBean(u);
        ArgumentCaptor<ConsoleViewUpdate> argument = ArgumentCaptor.forClass(ConsoleViewUpdate.class);
        controller.processUserAction(request);
        verify(view).processConsoleViewUpdate(argument.capture());
        ConsoleViewUpdate update = argument.getValue();
        assertThat(update.getMessages()).contains("UserNotExists");
        }


}