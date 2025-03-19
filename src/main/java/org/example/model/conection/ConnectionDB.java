package org.example.model.conection;

import org.example.utils.ManagerPDF;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {

    private final static String FILE = "connection.pdf";
    private static ConnectionDB _instance;
    private Connection connection;

    private ConnectionDB() {
        ConnectionProperties properties = ManagerPDF.readPDFToConnectionProperties(FILE);

        try {
            connection = DriverManager.getConnection(properties.getURL(), properties.getUser(), properties.getPassword());
        } catch (SQLException e) {
            e.printStackTrace();
            connection = null;
        }
    }

    public static Connection getConnection() {
        if (_instance == null || _instance.connection == null || isConnectionClosed()) {
            _instance = new ConnectionDB();
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