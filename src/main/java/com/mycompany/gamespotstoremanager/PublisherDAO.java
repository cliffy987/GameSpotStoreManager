/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gamespotstoremanager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class PublisherDAO {
    
    private static ArrayList<Publisher> allPublishersList;
    private static HashMap<Long, Publisher> publisherFromId;
    private static HashMap<String, Long> idFromPublisher;
    
    public static void setupIdPublishers() {
        //Already setup
        if (publisherFromId != null)
            return;
        
        allPublishersList = new ArrayList<Publisher>();
        publisherFromId = new HashMap<Long, Publisher>();
        idFromPublisher = new HashMap<String, Long>();
        //Create IdPublisher list
        try {
            Connection connection = DatabaseConnector.getConnection();

            String sql = "SELECT * FROM publishers";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            
            while (resultSet.next()) {
                long publisherId = resultSet.getInt("publisher_id");
                String publisherName = resultSet.getString("publisher_name");
                Publisher publisher = new Publisher(publisherId, publisherName);
                allPublishersList.add(publisher);
                publisherFromId.put(publisherId, publisher);
                idFromPublisher.put(publisherName, publisherId);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }
    
    public static void addPublisherToGame(long gameId, long publisherId) {
        String sql = "INSERT INTO game_publishers (game_id, publisher_id) VALUES (" + gameId + "," + publisherId + ")"; 
        DatabaseConnector.insertGetId(sql);
    }
    
    public static void removePublisherFromGame(long gameId, long publisherId) {
        String sql = "DELETE FROM game_publishers WHERE game_id = " + gameId + " AND publisher_id = " + publisherId; 
        DatabaseConnector.runOnDatabase(sql);
    }
    
    public static ArrayList<Publisher> getAllPublishers() {
        setupIdPublishers();
        return allPublishersList;
    }
    
    public static Publisher getPublisherFromId(long publisherId) {
        setupIdPublishers();
        return publisherFromId.get(publisherId);
    }
    
    public static long getIdFromPublisher(String publisherName) {
        setupIdPublishers();
        return idFromPublisher.get(publisherName);
    }
    
    public static boolean publisherNameExists(String publisherName) {
        setupIdPublishers();
        return idFromPublisher.containsKey(publisherName);
    }
    
    public static ArrayList<Publisher> getAllPublishersForGameId(long gameId) {
        setupIdPublishers();
        
        try {
            Connection connection = DatabaseConnector.getConnection();

            String sql = "SELECT * FROM game_publishers WHERE game_id = " + gameId;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            
            ArrayList<Publisher> gamePublishers = new ArrayList<Publisher>();
            
            //Get prevously-created object based on id
            while (resultSet.next()) {
                long publisherId = resultSet.getInt("publisher_id");
                gamePublishers.add(publisherFromId.get(publisherId));
            }
            
            return gamePublishers;
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static String getPublishersStringForGameId(long gameId) {
        try {
            Connection connection = DatabaseConnector.getConnection();

            String sql = "CALL get_publishers_string(?)";
            PreparedStatement call = connection.prepareCall(sql);
            call.setLong(1, gameId);
            ResultSet resultSet = call.executeQuery();
            resultSet.next();
            String result = resultSet.getString("publishers_string");
            return result;
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
