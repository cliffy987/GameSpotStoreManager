package com.mycompany.gamespotstoremanager;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
   
}
