package org.sistemaroti.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static final String URL = "jdbc:mysql://localhost:3306/rotiseria";
    private static final String USER = "root";
    private static final String PASS = "13579";

    public static Connection getConnection() {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("✅ Conexión exitosa");
            return conn;
        } catch (SQLException e) {
            System.err.println("❌ Error al conectar");
            e.printStackTrace();
            return null;
        }
    }

}
