package com.github.dmitriydb.etda.controller.console;

import com.github.dmitriydb.etda.dbutils.DbManager;
import com.github.dmitriydb.etda.model.EtdaModel;
import com.github.dmitriydb.etda.model.SimpleModel;
import com.github.dmitriydb.etda.model.dao.UserDAO;
import com.github.dmitriydb.etda.security.SecurityRole;
import com.github.dmitriydb.etda.security.User;
import com.github.dmitriydb.etda.view.console.*;
import com.sun.org.apache.xpath.internal.Arg;
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
import java.util.stream.Collectors;

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
    public void passingCorrectValuesToModelWhenCreatingObject(ConsoleViewOptions option){
        SimpleConsoleView view = mock(SimpleConsoleView.class);
        SimpleModel model = mock(SimpleModel.class);
        ConsoleController controller = Mockito.spy(new ConsoleController(model, view));
        ConsoleViewRequest request = new ConsoleViewRequest(option);
        try {
            Object entity = option.getEntityClass().newInstance();
            request.setBean(entity);

            controller.processUserAction(request);

            verify(model).createEntity(option.getEntityClass(), entity);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    @Test
    @Parameters(method = "updateOptions")
    public void passingCorrectValuesToModelWhenUpdatingObject(ConsoleViewOptions option){
        SimpleConsoleView view = mock(SimpleConsoleView.class);
        SimpleModel model = mock(SimpleModel.class);
        ConsoleController controller = Mockito.spy(new ConsoleController(model, view));
        ConsoleViewRequest request = new ConsoleViewRequest(option);
        try {
            Object entity = option.getEntityClass().newInstance();
            request.setBean(entity);

            controller.processUserAction(request);

            verify(model).updateEntity(option.getEntityClass(), entity);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    @Test
    @Parameters(method = "deleteOptions")
    public void passingCorrectValuesToModelWhenDeletingObject(ConsoleViewOptions option){
        SimpleConsoleView view = mock(SimpleConsoleView.class);
        SimpleModel model = mock(SimpleModel.class);
        ConsoleController controller = Mockito.spy(new ConsoleController(model, view));
        ConsoleViewRequest request = new ConsoleViewRequest(option);
        try {
            Object entity = option.getEntityClass().newInstance();
            request.setBean(entity);

            controller.processUserAction(request);

            verify(model).deleteEntity(option.getEntityClass(), null);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

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
        request.setId(null);
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

    @Test
    public void whenOperationShowThenStateWaitingUserKey(){
        SimpleConsoleView view = mock(SimpleConsoleView.class);
        ConsoleController controller = Mockito.spy(new ConsoleController(new SimpleModel(), view));
        ConsoleViewOptions option = ConsoleViewOptions.DEPARTMENTS_LIST;
        ConsoleViewRequest request = new ConsoleViewRequest(option);
        controller.processUserAction(request);
        verify(view).changeState(ViewState.WAITING_USER_KEY);
    }

    @Test
    public void whenRegisterUserDaoIsCreatingUserAndStateMenu(){
        SimpleConsoleView view = mock(SimpleConsoleView.class);
        ConsoleController controller = Mockito.spy(new ConsoleController(new SimpleModel(), view));
        UserDAO dao = mock(UserDAO.class);
        controller.setUserDAO(dao);
        User u = new User();
        String username = "test";
        u.setName(username);
        when(dao.isUserExists(username)).thenReturn(false);
        u.setSecurityRole(SecurityRole.EMPLOYEE_ROLE());
        ConsoleViewOptions option = ConsoleViewOptions.REGISTER;
        ConsoleViewRequest request = new ConsoleViewRequest(option);
        request.setBean(u);
        controller.processUserAction(request);
        verify(dao).createUser(u);
        verify(view).changeState(ViewState.MENU);
        verify(view).setUser(u);
    }

    @Test
    public void whenLoginUserDaoIsCheckingIfUserExists(){
        SimpleConsoleView view = mock(SimpleConsoleView.class);
        ConsoleController controller = Mockito.spy(new ConsoleController(new SimpleModel(), view));
        UserDAO dao = mock(UserDAO.class);
        controller.setUserDAO(dao);
        User u = new User();
        String username = "test";
        u.setName(username);
        when(dao.isUserExists(username)).thenReturn(false);
        u.setSecurityRole(SecurityRole.EMPLOYEE_ROLE());
        ConsoleViewOptions option = ConsoleViewOptions.LOGIN;
        ConsoleViewRequest request = new ConsoleViewRequest(option);
        request.setBean(u);

        controller.processUserAction(request);

        verify(dao).getUserByName(username);
        verify(view).changeState(ViewState.MENU);

    }


    @Test
    public void whenRegisterAndLoginExistsErrorLoginExists(){
        SimpleConsoleView view = mock(SimpleConsoleView.class);
        ConsoleController controller = Mockito.spy(new ConsoleController(new SimpleModel(), view));
        UserDAO dao = mock(UserDAO.class);
        controller.setUserDAO(dao);
        User u = new User();
        String username = "test";
        u.setName(username);
        when(dao.isUserExists(username)).thenReturn(true);
        u.setSecurityRole(SecurityRole.EMPLOYEE_ROLE());
        ConsoleViewOptions option = ConsoleViewOptions.REGISTER;
        ConsoleViewRequest request = new ConsoleViewRequest(option);
        request.setBean(u);
        ArgumentCaptor<ConsoleViewUpdate> argumentCaptor = ArgumentCaptor.forClass(ConsoleViewUpdate.class);

        controller.processUserAction(request);

        verify(view).processConsoleViewUpdate(argumentCaptor.capture());
        ConsoleViewUpdate consoleViewUpdate = argumentCaptor.getValue();
        
        verifyUpdateContains(consoleViewUpdate, "LoginExists");
        verify(dao, never()).createUser(u);
        verify(view).changeState(ViewState.MENU);
        verify(view, never()).setUser(u);
    }

    public void verifyUpdateContains(ConsoleViewUpdate consoleViewUpdate, String loginExists) {
        List<String> s = consoleViewUpdate.getMessages().stream().map(x -> x.toString()).collect(Collectors.toList());
        assertThat(s).contains(loginExists);
    }

    @Test
    public void whenRegisterSuccessfullyShowsMenuAndMessageSucRegister(){
        SimpleConsoleView view = mock(SimpleConsoleView.class);
        ConsoleController controller = Mockito.spy(new ConsoleController(new SimpleModel(), view));
        UserDAO dao = mock(UserDAO.class);
        controller.setUserDAO(dao);
        User u = new User();
        String username = "test";
        u.setName(username);
        when(dao.isUserExists(username)).thenReturn(false);
        u.setSecurityRole(SecurityRole.EMPLOYEE_ROLE());
        ConsoleViewOptions option = ConsoleViewOptions.REGISTER;
        ConsoleViewRequest request = new ConsoleViewRequest(option);
        request.setBean(u);
        ArgumentCaptor<ConsoleViewUpdate> argumentCaptor = ArgumentCaptor.forClass(ConsoleViewUpdate.class);

        controller.processUserAction(request);

        verify(view).processConsoleViewUpdate(argumentCaptor.capture());
        ConsoleViewUpdate consoleViewUpdate = argumentCaptor.getValue();

        verifyUpdateContains(consoleViewUpdate, "SucRegister");
    }

    @Test
    public void whenRegisterAsAdminSetsAdminRole(){
        SimpleConsoleView view = mock(SimpleConsoleView.class);
        ConsoleController controller = Mockito.spy(new ConsoleController(new SimpleModel(), view));
        UserDAO dao = mock(UserDAO.class);
        controller.setUserDAO(dao);
        User u = new User();
        String username = "admin";
        u.setName(username);
        when(dao.isUserExists(username)).thenReturn(false);

        ConsoleViewOptions option = ConsoleViewOptions.REGISTER;
        ConsoleViewRequest request = new ConsoleViewRequest(option);
        request.setBean(u);
        ArgumentCaptor<User> argumentCaptor = ArgumentCaptor.forClass(User.class);

        controller.processUserAction(request);

        verify(view).setUser(argumentCaptor.capture());
        User uTest = argumentCaptor.getValue();

        assertThat(u).isEqualTo(uTest);
        assertThat(u.getSecurityRole()).isEqualTo(SecurityRole.ADMIN_ROLE());

    }

    @Test
    public void whenRegisterFailsShowErrorRegisterMessage(){
        SimpleConsoleView view = mock(SimpleConsoleView.class);
        ConsoleController controller = Mockito.spy(new ConsoleController(new SimpleModel(), view));
        UserDAO dao = mock(UserDAO.class);
        controller.setUserDAO(dao);
        User u = new User();
        String username = null;
        u.setName(username);
        when(dao.isUserExists(username)).thenReturn(false);

        ConsoleViewOptions option = ConsoleViewOptions.REGISTER;
        ConsoleViewRequest request = new ConsoleViewRequest(option);
        request.setBean(u);
        ArgumentCaptor<ConsoleViewUpdate> argumentCaptor = ArgumentCaptor.forClass(ConsoleViewUpdate.class);

        controller.processUserAction(request);

        verify(view).processConsoleViewUpdate(argumentCaptor.capture());
        ConsoleViewUpdate update = argumentCaptor.getValue();

        verifyUpdateContains(update, "ErrorRegister");

    }
}