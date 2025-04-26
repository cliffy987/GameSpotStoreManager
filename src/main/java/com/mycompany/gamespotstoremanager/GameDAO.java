/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gamespotstoremanager;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author ebwhi
 */
public class GameDAO {
    
    private static ArrayList<Game> gamesList;
    private static HashMap<Long, Game> IdGames;
    private static HashMap<Integer, String> IdEsrb;
    
    public static ArrayList<Game> getAllGames() {
        //Don't recreate if we already have the list made!
        if (gamesList != null)
            return gamesList;

        gamesList = new ArrayList<Game>();
        IdGames = new HashMap<Long, Game>();
        
        try {
        Connection connection = DatabaseConnector.getConnection();
        
        String gameSql = "SELECT * FROM game_esrb";
        Statement gameStatement = connection.createStatement();
        ResultSet resultSet = gameStatement.executeQuery(gameSql);

        while (resultSet.next()) { 
            //Create an object for each game
            long gameId = resultSet.getLong("game_id");
            String gameName = resultSet.getString("game_name");
            Date releaseDate = resultSet.getDate("release_date");
            int esrbId = resultSet.getInt("esrb_id");
            String esrbRating = resultSet.getString("rating");
            
            ArrayList<Genre> genreList = GenreDAO.getAllGenresForGameId(gameId);
            ArrayList<Publisher> publisherList = PublisherDAO.getAllPublishersForGameId(gameId);
            
            Game game = new Game(gameId, gameName, releaseDate, esrbId, esrbRating, genreList, publisherList);
            gamesList.add(game);
            IdGames.put(gameId, game);
        }
        
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return gamesList;
    }
    
    public static void addGameToDatabase(Game game) {
        try {
            Connection connection = DatabaseConnector.getConnection();

            //The actual Game entry
            String gameSql = "INSERT INTO games (game_name,release_date,esrb_id) " +
                         "VALUES (?, ?, ?)";
            PreparedStatement gamePstmt = connection.prepareStatement(gameSql);

            gamePstmt.setString(1, game.getGameName()); //game_name
            gamePstmt.setDate(2, game.getReleaseDate()); //release_date
            gamePstmt.setInt(3, game.getEsrbId()); //esrb_id
            
            gamePstmt.executeUpdate();
            
            //Get Game Id
            String gameIdSql = "SELECT LAST_INSERT_ID() AS game_id";
            Statement gameIdStatement = connection.createStatement();
            ResultSet gameIdResultSet = gameIdStatement.executeQuery(gameIdSql);
            gameIdResultSet.next();
            long gameId = gameIdResultSet.getLong("game_id");
            game.setGameId(gameId);
            
            //Genre connections
            for (int i = 0; i < game.genreList.size(); i++) {
                Genre genre = game.genreList.get(i);
                String genreSql = "INSERT INTO game_genres (game_id,genre_id)" + 
                                    "VALUES (?, ?)";
                
                PreparedStatement genrePstmt = connection.prepareStatement(genreSql);
                
                genrePstmt.setLong(1, game.getGameId());
                genrePstmt.setLong(2, genre.getGenreId());
                
                genrePstmt.executeUpdate();
            }
            
            //Publisher connections
            for (int i = 0; i < game.publisherList.size(); i++) {
                Publisher publisher = game.publisherList.get(i);
                String publisherSql = "INSERT INTO game_publishers (game_id,publisher_id)" + 
                                    "VALUES (?, ?)";
                
                PreparedStatement publisherPstmt = connection.prepareStatement(publisherSql);
                
                publisherPstmt.setLong(1, game.getGameId());
                publisherPstmt.setLong(2, publisher.getPublisherId());
                
                publisherPstmt.executeUpdate();
            }
            
            
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }
    
    private static void setupIdEsrb() {
        if (IdEsrb != null)
            return;
        
        IdEsrb = new HashMap<Integer, String>();
        
        try {
        Connection connection = DatabaseConnector.getConnection();
        
        String sql = "SELECT * FROM esrb";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) { 
            //Create an object for each game
            int esrbId = resultSet.getInt("esrb_id");
            String esrbRating = resultSet.getString("rating");
            IdEsrb.put(esrbId, esrbRating);
        }
        
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }
    
    public static String getEsrbFromId(Integer esrbId) {
        setupIdEsrb();
        return IdEsrb.get(esrbId);
    }
    
    public static ArrayList<Game> searchGames(String name, String genre, String publisher, int esrbId) {
        return null;
    }
}
