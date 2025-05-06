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
public class GameAuditDataDAO {
    
    public static void addAudit(long gameId, String description) {
        String sql = "CALL log_game_update(" + gameId + ",\"" + description + "\")";
        DatabaseConnector.runOnDatabase(sql);
    }
    
    public static ArrayList<GameAuditData> getAllGameAudits() {
        try (Connection connection = DatabaseConnector.getConnection()) {

            String sql = "SELECT * FROM game_audit_data";
            Statement stmt = connection.createStatement();
            ResultSet res = stmt.executeQuery(sql);
            
            ArrayList<GameAuditData> auditList = new ArrayList<>();
            
            while (res.next()) {
                String name = res.getString("name");
                String description = res.getString("description");
                Timestamp timestamp = res.getTimestamp("timestamp");
                
                GameAuditData audit = new GameAuditData(name, description, timestamp);
                auditList.add(audit);
            }
            
            return auditList;
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }
}
