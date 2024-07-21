package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexiuneBazaDate {
    private static final String URL = "jdbc:mysql://localhost:3306/restaurantdb";
    private static final String USER = "root"; 
    private static final String PASSWORD = "root"; 

    public static Connection obtineConexiune() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Eroare la conectarea la baza de date!", e);
        }
    }
}
