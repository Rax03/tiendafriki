package org.example.model.conection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {

    private static ConnectionDB _instance;
    private Connection connection;

    private ConnectionDB(String url, String user, String password) {
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
            connection = null;
        }
    }

    public static Connection getConnection(String url, String user, String password) {
        if (_instance == null || _instance.connection == null || isConnectionClosed()) {
            _instance = new ConnectionDB(url, user, password);
        }
        return _instance.connection;
    }

    public static void closeConnection() {
        if (_instance != null && _instance.connection != null) {
            try {
                _instance.connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static boolean isConnectionClosed() {
        try {
            return _instance.connection == null || _instance.connection.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
    }
}