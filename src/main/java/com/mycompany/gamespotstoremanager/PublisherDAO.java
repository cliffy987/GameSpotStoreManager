/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gamespotstoremanager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class PublisherDAO {
    
    private static ArrayList<Publisher> allPublishersList;
    private static HashMap<Long, Publisher> IdPublishers;
    
    public static void setupIdPublishers() {
        //Already setup
        if (IdPublishers != null)
            return;
        
        allPublishersList = new ArrayList<Publisher>();
        IdPublishers = new HashMap<Long, Publisher>();
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
                IdPublishers.put(publisherId, publisher);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }
    
    public static ArrayList<Publisher> getAllPublishers() {
        setupIdPublishers();
        return allPublishersList;
    }
    
    public static Publisher getPublisherFromId(long publisherId) {
        setupIdPublishers();
        return IdPublishers.get(publisherId);
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
                gamePublishers.add(IdPublishers.get(publisherId));
            }
            
            return gamePublishers;
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }
}
