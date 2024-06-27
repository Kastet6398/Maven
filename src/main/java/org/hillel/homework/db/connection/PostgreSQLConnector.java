package org.hillel.homework.db.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgreSQLConnector extends Connector {
    private static final int DEFAULT_PORT = 5432;
    private static final String DEFAULT_HOST = "127.0.0.1";

    private static Connection connection;

    public static Connection connection(String host, int port, String db, String username, String password) throws SQLException {
        if (connection == null) {
            connection = DriverManager.getConnection(STR."jdbc:postgresql://\{host}:\{port}/\{db}", username, password);
        }

        return connection;
    }

    public static Connection connection(String host, String db, String username, String password) throws SQLException {
        return connection(host, DEFAULT_PORT, db, username, password);
    }


    public static Connection connection(int port, String db, String username, String password) throws SQLException {
        return connection(DEFAULT_HOST, port, db, username, password);
    }

    public static Connection connection(String db, String username, String password) throws SQLException {
        return connection(DEFAULT_PORT, db, username, password);
    }
    public static void reset() {
        connection = null;
    }
}
