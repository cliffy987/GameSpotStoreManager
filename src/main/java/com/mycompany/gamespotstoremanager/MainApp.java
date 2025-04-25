/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.gamespotstoremanager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author ebwhi
 */
public class MainApp extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("MainMenu"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        testCreateAndAddGame();
        launch();
    }
    
    //////////////////////////////
    //Test
    //////////////////////////////
    
    public static void testCreateAndAddGame() {
        ArrayList genreList = new ArrayList<Genre>();
        ArrayList publisherList = new ArrayList<Publisher>();
        genreList.add(GenreDAO.getGenreFromId(1)); //Action
        genreList.add(GenreDAO.getGenreFromId(2)); //Adventure
        publisherList.add(PublisherDAO.getPublisherFromId(1)); //NovaCore Games
        publisherList.add(PublisherDAO.getPublisherFromId(2)); //IronPixel Studios
        
        Date date = java.sql.Date.valueOf("1971-12-10");
        Game game = new Game("Super Chicken Adventure", date, 2, genreList, publisherList);
        
        GameDAO.addGameToDatabase(game);
        System.out.println("Game Id: " + game.getGameId());
    }
}
