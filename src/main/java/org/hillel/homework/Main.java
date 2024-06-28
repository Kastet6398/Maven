package org.hillel.homework;

import org.hillel.homework.db.dao.AppStoreDaoImpl;
import org.hillel.homework.main.AppStoreApp;
import org.hillel.homework.constant.Constant;
import org.hillel.homework.db.connection.PostgreSQLConnector;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        try (Connection connection = PostgreSQLConnector.connection(
                System.getenv("DB_HOST"),
                Integer.parseInt(System.getenv("DB_PORT")),
                System.getenv("DB_NAME"),
                System.getenv("DB_USER"),
                System.getenv("DB_PASSWORD")
        )) {
            AppStoreDaoImpl dao = new AppStoreDaoImpl(connection);
            AppStoreApp appStoreApp = new AppStoreApp(dao);
            appStoreApp.start();
        } catch (Exception e) {
            System.out.println(Constant.ERROR);
        }
    }
}
