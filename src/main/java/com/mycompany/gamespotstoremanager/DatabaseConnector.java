package com.mycompany.gamespotstoremanager;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ebwhi
 */
public class DatabaseConnector {
    //Change to new database
    private static final String URL = "jdbc:mysql://localhost:3306/gamespot";
            
    private static final String USER = "root";
    private static final String PASSWORD = "cheeseland";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    
    public static void runOnDatabase(String sql) {
        try (Connection connection = DatabaseConnector.getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static long insertGetId(String sql) {
        try (Connection connection = DatabaseConnector.getConnection()) {
            Statement gameStatement = connection.createStatement();
            gameStatement.execute(sql);
            
            String idSql = "SELECT LAST_INSERT_ID() AS id";
            Statement idStatement = connection.createStatement();
            ResultSet idResultSet = idStatement.executeQuery(idSql);
            idResultSet.next();
            long id = idResultSet.getLong("id");
            return id;
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return -1;

    }
   
}
