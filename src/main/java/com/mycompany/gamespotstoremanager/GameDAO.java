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
        
        try (Connection connection = DatabaseConnector.getConnection()) {
        
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
    
    public static void addGameToDatabase(Game game, double newPrice, long newQuantity) {
        try (Connection connection = DatabaseConnector.getConnection()) {

            //The actual Game entry
            String gameSql = "INSERT INTO games (game_name,release_date,esrb_id,new_copy_price,new_copy_quantity) " +
                         "VALUES (?, ?, ?, ?, ?)";
            PreparedStatement gamePstmt = connection.prepareStatement(gameSql);

            gamePstmt.setString(1, game.getGameName()); //game_name
            gamePstmt.setDate(2, game.getReleaseDate()); //release_date
            gamePstmt.setInt(3, game.getEsrbId()); //esrb_id
            gamePstmt.setDouble(4, newPrice); //price
            gamePstmt.setLong(5, newQuantity); //quantity
            
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
    
    public static void updateGameName(long gameId, String newName) {
        String sql = "UPDATE games SET game_name = \'" + newName + "\' WHERE game_id = " + gameId; 
        DatabaseConnector.runOnDatabase(sql);
    }
    
    public static void updateGameESRB(long gameId, int esrbId) {
        String sql = "UPDATE games SET esrb_id = " + esrbId + " WHERE game_id = " + gameId; 
        DatabaseConnector.runOnDatabase(sql);
    }
    
    public static void updateGamePrice(long gameId, double newPrice) {
        String sql = "UPDATE games SET new_copy_price = "+ newPrice +" WHERE game_id = "+ gameId;
        DatabaseConnector.runOnDatabase(sql);
    }
    
    private static void setupIdEsrb() {
        if (IdEsrb != null)
            return;
        
        IdEsrb = new HashMap<Integer, String>();
        
        try (Connection connection = DatabaseConnector.getConnection()) {
        
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
    
    private static String concatWhereSql(String current, String concat) {
        if (current.isEmpty())
            current += " WHERE " + concat;
        else
            current += " AND " + concat;
        return current;
    }
    
    public static long adjustNewQuantity(long gameId, long adjustment) {
        try (Connection connection = DatabaseConnector.getConnection()) {
        
            String gameSql = "SELECT * FROM games WHERE game_id = " + gameId;
            Statement gameStatement = connection.createStatement();
            ResultSet resultSet = gameStatement.executeQuery(gameSql);
            resultSet.next(); //There can only be one result, since game_id has the UNIQUE constraint
            long gameQuantity = resultSet.getLong("new_copy_quantity");
            long adjustedGameQuantity = gameQuantity + adjustment;
            
            String updateSql = "UPDATE games SET new_copy_quantity = "+ adjustedGameQuantity +" WHERE game_id = "+ gameId;
            Statement updateStatement = connection.createStatement();
            updateStatement.execute(updateSql);
            
            return adjustedGameQuantity;
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return -1;
    }
    
    public static GamePurchaseData getNewGamePurchaseData(GameData gameSearch) {
        try (Connection connection = DatabaseConnector.getConnection()) {
        
            String gameSql = "SELECT * FROM games WHERE game_id = " + gameSearch.getGameId();
            Statement gameStatement = connection.createStatement();
            ResultSet resultSet = gameStatement.executeQuery(gameSql);
            resultSet.next(); //There can only be one result, since game_id has the UNIQUE constraint
            double gamePrice = resultSet.getDouble("new_copy_price");
            long gameQuantity = resultSet.getLong("new_copy_quantity");
            GamePurchaseData gamePurchase = new GamePurchaseData(gameSearch, gamePrice, gameQuantity);
            
            return gamePurchase;
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static ArrayList<GameData> searchGames(String name, String genre, String publisher, int esrbId) {
        try (Connection connection = DatabaseConnector.getConnection()) {
        
            ArrayList<GameData> gameSearchList = new ArrayList<>();

            String sql = "SELECT DISTINCT search_game_id, search_game_name, search_esrb_id FROM game_search";
            String whereSql = "";

            //Add search conditions if they're wanted
            if (name.isEmpty() == false) {
                String concatString = "search_game_name = " + "\"" + name + "\"";
                whereSql = concatWhereSql(whereSql, concatString);
            }
            if (genre.isEmpty() == false) {
                String concatString = "search_genre_name = " + "\"" + genre + "\"";
                whereSql = concatWhereSql(whereSql, concatString);
            }
            if (publisher.isEmpty() == false) {
                String concatString = "search_publisher_name = " + "\"" + publisher + "\"";
                whereSql = concatWhereSql(whereSql, concatString);
            }
            if (esrbId != -1) {
                String concatString = "search_esrb_id = " + esrbId;
                whereSql = concatWhereSql(whereSql, concatString);
            }
            
            String finalSql = sql + whereSql;
            
            System.out.println(finalSql);

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(finalSql);

            //Create object for each search result
            while (resultSet.next()) { 
                //(search_game_id, search_game_name, search_genre_name, search_publisher_name, search_esrb_id)
                long gameId = resultSet.getLong("search_game_id");
                String gameName = resultSet.getString("search_game_name");
                String gameGenres = GenreDAO.getGenresStringForGameId(gameId);
                String gamePublishers = PublisherDAO.getPublishersStringForGameId(gameId);
                String gameRating = getEsrbFromId(resultSet.getInt("search_esrb_id"));

                GameData gameSearch = new GameData(gameId, gameName, gameGenres, gamePublishers, gameRating);
                gameSearchList.add(gameSearch);
            }
            
            return gameSearchList;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }
}
