/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gamespotstoremanager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author ebwhi
 */
public class PurchaseRecordDAO {
    public static ArrayList<PurchaseRecord> getAllPurchaseRecords() {
        try (Connection connection = DatabaseConnector.getConnection()) {
            
            String sql = "SELECT * FROM purchase_records";
            Statement stmt = connection.createStatement();
            ResultSet res = stmt.executeQuery(sql);
            
            ArrayList<PurchaseRecord> prList = new ArrayList<>();
            
            while (res.next()) {
                String name = res.getString("name");
                double price = res.getDouble("price");
                String condition = res.getString("game_condition");
                Timestamp timestamp = res.getTimestamp("timestamp");
                
                PurchaseRecord pr = new PurchaseRecord(name, price, condition, timestamp);
                prList.add(pr);
            }
            
            return prList;
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
