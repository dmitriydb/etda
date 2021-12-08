package com.github.dmitriydb.etda.consoleapp;

import com.github.dmitriydb.etda.controller.console.ConsoleController;
import com.github.dmitriydb.etda.dbutils.DbManager;
import com.github.dmitriydb.etda.view.console.SimpleConsoleView;
import com.github.dmitriydb.etda.view.console.ViewState;
import org.hibernate.Session;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ConsoleAppTest {

    @Test
    public void consoleAppStartingControllerAndCallingDbManagerInit(){
        ConsoleApp consoleApp = new ConsoleApp();
        DbManager manager = Mockito.mock(DbManager.class);
        ConsoleController controller = Mockito.mock(ConsoleController.class);
        consoleApp.start(manager, controller);
        verify(manager, times(1)).initSelf();
        verify(controller, times(1)).startWork();
    }

    @Test
    public void sessionFactoryIsCreatedWhenStartingConsoleApp(){
        ConsoleApp consoleApp = new ConsoleApp();
        DbManager manager = Mockito.mock(DbManager.class);
        ConsoleController controller = Mockito.mock(ConsoleController.class);
        doCallRealMethod().when(manager).initSelf();

        consoleApp.start(manager, controller);

        Session session = DbManager.getSession();
        assertNotNull(session);
    }

    @Test
    public void viewStateInitCorrectly(){
        ConsoleApp consoleApp = new ConsoleApp();
        DbManager manager = Mockito.mock(DbManager.class);
        ConsoleController controller = new ConsoleController();
        ConsoleController controllerSpy = Mockito.spy(controller);
        assertEquals(controllerSpy.getView().getCurrentState(), ViewState.CREATED);
    }

}