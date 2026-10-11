package com.patitasfelices.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    // Los datos ahora se jalan de manera segura desde app.properties
    private static final String URL = Config.get("db.url");
    private static final String USER = Config.get("db.user");
    private static final String PASS = Config.get("db.pass");

    static {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("No se encontro el driver de SQL Server. Agrega mssql-jdbc al proyecto.", e);
        }
    }

    public static Connection getConexion() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}