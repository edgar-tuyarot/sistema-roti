package org.sistemaroti.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static final String URL = "jdbc:mysql://sql.freedb.tech:3306/freedb_proyecto_roti";
    private static final String USER = "freedb_edgar";
    private static final String PASS = "v4sXW7$%R9kVA*Q";

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
