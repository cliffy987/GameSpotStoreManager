/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gamespotstoremanager;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author ebwhi
 */
public class UsedGameDAO {
    
    private static HashMap<Integer, String> idCondition;
    
    private static void setupIdCondition() {
        if (idCondition != null)
            return;
        
        idCondition = new HashMap<Integer, String>();
        
        
        try (Connection connection = DatabaseConnector.getConnection()) {
            Statement gameStatement = connection.createStatement();
            ResultSet resultSet = gameStatement.executeQuery("SELECT * FROM game_condition");

            while (resultSet.next()) { 
                //Create an object for each game
                int conditionId = resultSet.getInt("game_condition_id");
                String gameCondition = resultSet.getString("game_condition");
                idCondition.put(conditionId, gameCondition);
            }
        
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }
    
    public static int getConditionIdFromString(String condition) {
        String sql = "{CALL condition_id_from_string(?,?)}";
        
        try (Connection connection = DatabaseConnector.getConnection()) {
            CallableStatement cs = connection.prepareCall(sql);
            cs.setString(1, condition);
            cs.registerOutParameter(2, Types.INTEGER);
            cs.execute();
            int conditionId = cs.getInt(2);
            return conditionId;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return -1;
    }
    
    public static void removeUsedGame(long usedId) {
        String sql = "DELETE FROM used_game_stock WHERE used_id = " + usedId; 
        DatabaseConnector.runOnDatabase(sql);
    }
    
    public static String getConditionFromId(Integer conditionId) {
        setupIdCondition();
        return idCondition.get(conditionId);
    }

    public static ArrayList<UsedGame> getUsedGamesFromGameData(GameData gameData) {
        
        ArrayList<UsedGame> usedGames = new ArrayList<>();
        
        String sql = "SELECT * FROM used_game_stock WHERE game_id = " + gameData.getGameId();
        
        try (Connection connection = DatabaseConnector.getConnection()) {
            Statement gameStatement = connection.createStatement();
            ResultSet usedGameResults = gameStatement.executeQuery(sql);
            
            while (usedGameResults.next()) {
                long usedId = usedGameResults.getLong("used_id");
                double price = usedGameResults.getDouble("price");
                String condition = getConditionFromId(usedGameResults.getInt("game_condition_id"));

                UsedGame usedGame = new UsedGame(usedId, gameData, price, condition);
                usedGames.add(usedGame);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return usedGames;
    }
}
