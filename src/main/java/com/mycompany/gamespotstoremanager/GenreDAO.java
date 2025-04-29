/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gamespotstoremanager;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class GenreDAO {
    private static ArrayList<Genre> allGenresList;
    private static HashMap<Long, Genre> IdGenres;
    
    public static void setupIdGenres() {
        //Already setup
        if (IdGenres != null)
            return;
        
        allGenresList = new ArrayList<Genre>();
        IdGenres = new HashMap<Long, Genre>();
        //Create IdGenre list
        try (Connection connection = DatabaseConnector.getConnection()) {

            String sql = "SELECT * FROM genres";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            
            while (resultSet.next()) {
                long genreId = resultSet.getInt("genre_id");
                String genreName = resultSet.getString("genre_name");
                Genre genre = new Genre(genreId, genreName);
                allGenresList.add(genre);
                IdGenres.put(genreId, genre);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }
    
    public static ArrayList<Genre> getAllGenres() {
        setupIdGenres();
        return allGenresList;
    }
    
    public static Genre getGenreFromId(long genreId) {
        setupIdGenres();
        return IdGenres.get(genreId);
    }
    
    public static ArrayList<Genre> getAllGenresForGameId(long gameId) {
        setupIdGenres();
        
        try (Connection connection = DatabaseConnector.getConnection()) {

            String sql = "SELECT * FROM game_genres WHERE game_id = " + gameId;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            
            ArrayList<Genre> gameGenres = new ArrayList<Genre>();
            
            //Get prevously-created object based on id
            while (resultSet.next()) {
                long genreId = resultSet.getInt("genre_id");
                gameGenres.add(IdGenres.get(genreId));
            }
            
            return gameGenres;
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static String getGenresStringForGameId(long gameId) {
        try (Connection connection = DatabaseConnector.getConnection()) {

            String sql = "CALL get_genres_string(?)";
            PreparedStatement call = connection.prepareCall(sql);
            call.setLong(1, gameId);
            ResultSet resultSet = call.executeQuery();
            resultSet.next();
            String result = resultSet.getString("genres_string");
            return result;
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
