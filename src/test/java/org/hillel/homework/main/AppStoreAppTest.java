package org.hillel.homework.main;

import org.hillel.homework.constant.Constant;
import org.hillel.homework.db.dao.AppStoreDao;
import org.hillel.homework.entity.AppEntity;
import org.hillel.homework.exception.DuplicateException;
import org.hillel.homework.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AppStoreAppTest {
    private AppStoreApp appStoreApp;
    private AppStoreDao dao;
    private Scanner scanner;
    private static final List<AppEntity> VALID_APPS = Arrays.asList(new AppEntity(null, Date.valueOf("1111-11-11"), 0, 0, null, Timestamp.valueOf("1111-11-11 00:00:00"), null, 1), new AppEntity(null, Date.valueOf("1111-11-11"), 0, 0, null, Timestamp.valueOf("1111-11-10 23:00:00"), null, 2));

    @BeforeEach
    void setUp() {
        dao = mock(AppStoreDao.class);
        scanner = mock(Scanner.class);
        appStoreApp = spy(new AppStoreApp(dao));
        appStoreApp.setScanner(scanner);
    }

    @Test
    void testStart_CreateTableSQLException() throws SQLException {
        doThrow(SQLException.class).when(dao).createTable();
        appStoreApp.start();
        verify(dao).createTable();
    }

    @Test
    void testMenu_InvalidInput() {
        when(scanner.nextLine()).thenReturn("invalid");
        assertEquals(-1, appStoreApp.menu());
    }

    @Test
    void testAdd_DuplicateException() throws SQLException, DuplicateException {
        AppEntity app = VALID_APPS.getFirst();
        when(scanner.nextLine()).thenReturn(app.getName(), String.valueOf(app.getRating()), String.valueOf(app.getCost()), app.getDescription(), app.getType(), app.getReleaseDate().toString());
        doThrow(DuplicateException.class).when(dao).create(any(AppEntity.class));
        appStoreApp.add();
        verify(dao).create(any(AppEntity.class));
        verify(appStoreApp).display(Constant.DUPLICATE);
    }

    @Test
    void testAdd_ValidArgs() throws SQLException, DuplicateException {
        AppEntity app = VALID_APPS.getFirst();
        when(scanner.nextLine()).thenReturn(app.getName(), String.valueOf(app.getRating()), String.valueOf(app.getCost()), app.getDescription(), app.getType(), app.getReleaseDate().toString());
        appStoreApp.add();
        verify(dao).create(any(AppEntity.class));
    }

    @Test
    void testRemove_InvalidIdInput() throws SQLException, NotFoundException {
        when(scanner.nextLine()).thenReturn("invalid", "1");
        appStoreApp.remove();
        verify(appStoreApp, times(2)).display(Constant.ID_PROMPT, false);
        verify(dao).delete(1L);
        verify(appStoreApp).display(Constant.TRY_AGAIN);
    }

    @Test
    void testRemove_ValidId() throws SQLException, NotFoundException {
        when(scanner.nextLine()).thenReturn("1");
        appStoreApp.remove();
        verify(appStoreApp).display(Constant.ID_PROMPT, false);
        verify(dao).delete(1L);
    }

    @Test
    void testRemove_NotFoundException() throws SQLException, NotFoundException {
        when(scanner.nextLine()).thenReturn("1");
        doThrow(NotFoundException.class).when(dao).delete(1L);
        appStoreApp.remove();
        verify(appStoreApp).display(Constant.ID_PROMPT, false);
        verify(dao).delete(1L);
        verify(appStoreApp).display(Constant.NOT_FOUND);
    }

    @Test
    void testListApps_NoApps() throws SQLException {
        when(dao.fetchAll()).thenReturn(Collections.emptyList());
        appStoreApp.listApps();
        verify(dao).fetchAll();
        verify(appStoreApp).display(Constant.NO_APPS);
    }

    @Test
    void testListApps_WithApps() throws SQLException {
        when(dao.fetchAll()).thenReturn(VALID_APPS);
        appStoreApp.listApps();
        verify(dao).fetchAll();
        verify(appStoreApp).display(VALID_APPS.get(0));
        verify(appStoreApp).display(VALID_APPS.get(1));
    }

    @Test
    void testSearchByName_NotFoundException() throws SQLException, NotFoundException {
        when(scanner.nextLine()).thenReturn("App Name");
        doThrow(NotFoundException.class).when(dao).getByName("App Name");
        appStoreApp.searchByName();
        verify(dao).getByName("App Name");
    }

    @Test
    void testSearchByName_Found() throws SQLException, NotFoundException {
        AppEntity appEntity = new AppEntity();
        when(scanner.nextLine()).thenReturn("App Name");
        when(dao.getByName("App Name")).thenReturn(appEntity);
        appStoreApp.searchByName();
        verify(appStoreApp).display(Constant.NAME_PROMPT, false);
        verify(dao).getByName("App Name");
        verify(appStoreApp).display(appEntity);
    }

    @Test
    void testFilterByType_NoApps() throws SQLException {
        when(scanner.nextLine()).thenReturn("Type");
        when(dao.filterByType("Type")).thenReturn(Collections.emptyList());
        appStoreApp.filterByType();
        verify(appStoreApp).display(Constant.TYPE_PROMPT, false);
        verify(dao).filterByType("Type");
        verify(appStoreApp).display(Constant.NO_APPS);
    }

    @Test
    void testFilterByType_WithApps() throws SQLException {
        when(scanner.nextLine()).thenReturn("Type");
        when(dao.filterByType("Type")).thenReturn(VALID_APPS);
        appStoreApp.filterByType();
        verify(appStoreApp).display(Constant.TYPE_PROMPT, false);
        verify(dao).filterByType("Type");
        verify(appStoreApp).display(VALID_APPS.get(0));
        verify(appStoreApp).display(VALID_APPS.get(1));
    }

    @Test
    void testFilterByPrice_InvalidCostInput() throws SQLException {
        when(scanner.nextLine()).thenReturn("invalid", "1");
        appStoreApp.filterByPrice();
        verify(appStoreApp, times(2)).display(Constant.COST_PROMPT, false);
        verify(dao).filterByPrice(1);
        verify(appStoreApp).display(Constant.TRY_AGAIN);
    }

    @Test
    void testFilterByPrice_NoApps() throws SQLException {
        when(scanner.nextLine()).thenReturn("10.0");
        when(dao.filterByPrice(10.0)).thenReturn(Collections.emptyList());
        appStoreApp.filterByPrice();
        verify(appStoreApp).display(Constant.COST_PROMPT, false);
        verify(dao).filterByPrice(10.0);
        verify(appStoreApp).display(Constant.NO_APPS);
    }

    @Test
    void testFilterByPrice_WithApps() throws SQLException {
        List<AppEntity> VALID_APPS = Arrays.asList(new AppEntity(null, null, 0, 0, null, Timestamp.valueOf("1111-11-11 00:00:00"), null, 1), new AppEntity(null, null, 0, 0, null, Timestamp.valueOf("1111-11-10 23:00:00"), null, 2));
        when(scanner.nextLine()).thenReturn("10.0");
        when(dao.filterByPrice(10.0)).thenReturn(VALID_APPS);
        appStoreApp.filterByPrice();
        verify(appStoreApp).display(Constant.COST_PROMPT, false);
        verify(dao).filterByPrice(10.0);
        verify(appStoreApp).display(VALID_APPS.get(0));
        verify(appStoreApp).display(VALID_APPS.get(1));
    }

    @Test
    void testSortByCreationDate_NoApps() throws SQLException {
        when(dao.sortByCreationDate()).thenReturn(Collections.emptyList());
        appStoreApp.sortByCreationDate();
        verify(dao).sortByCreationDate();
        verify(appStoreApp).display(Constant.NO_APPS);
    }

    @Test
    void testSortByCreationDate_WithApps() throws SQLException {
        List<AppEntity> VALID_APPS = Arrays.asList(new AppEntity(null, null, 0, 0, null, Timestamp.valueOf("1111-11-11 00:00:00"), null, 1), new AppEntity(null, null, 0, 0, null, Timestamp.valueOf("1111-11-10 23:00:00"), null, 2));
        when(dao.sortByCreationDate()).thenReturn(VALID_APPS);
        appStoreApp.sortByCreationDate();
        verify(dao).sortByCreationDate();
        verify(appStoreApp).display(VALID_APPS.get(1));
        verify(appStoreApp).display(VALID_APPS.get(0));
    }
}
