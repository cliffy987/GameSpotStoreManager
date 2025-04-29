package com.mycompany.gamespotstoremanager;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

public class FXGameSearchController extends FXController {

    @FXML private TextField nameField;
    @FXML private TextField genreField;
    @FXML private TextField publisherField;
    @FXML private MenuButton ratingMenu;
    @FXML private Text ratingText;
    @FXML private TableView<GameSearchData> gameViewTable;
    @FXML private TableColumn<GameSearchData, String> nameColumn;
    @FXML private TableColumn<GameSearchData, String> genresColumn;
    @FXML private TableColumn<GameSearchData, String> publishersColumn;
    @FXML private TableColumn<GameSearchData, String> ratingColumn;
    
    private String nameEntered = "";
    private String genreEntered = "";
    private String publisherEntered = "";
    private int esrbId = -1;
    
    
    
    @FXML
    private void nameFieldUpdated() {
        nameEntered = nameField.getText();
    }
    
    @FXML
    private void genreFieldUpdated() {
        genreEntered = genreField.getText();
    }
    
    @FXML
    private void publisherFieldUpdated() {
        publisherEntered = publisherField.getText();
    }
    
    @FXML
    private void searchPressed() {
        //Just in case user didn't press "Enter"
        nameFieldUpdated();
        genreFieldUpdated();
        publisherFieldUpdated();
        
        //Make use of GameDAO's searchGames method to get an array of games search data
        ArrayList<GameSearchData> gameSearchList = GameDAO.searchGames(nameEntered, genreEntered, publisherEntered, esrbId);
        
        //Create setCellValueFactories and display the search results
        ObservableList<GameSearchData> observableGames = FXCollections.observableArrayList(gameSearchList);

        nameColumn.setCellValueFactory(new PropertyValueFactory<GameSearchData, String>("gameName"));
        genresColumn.setCellValueFactory(new PropertyValueFactory<GameSearchData, String>("gameGenres"));
        publishersColumn.setCellValueFactory(new PropertyValueFactory<GameSearchData, String>("gamePublishers"));
        ratingColumn.setCellValueFactory(new PropertyValueFactory<GameSearchData, String>("gameRating"));

        gameViewTable.setItems(observableGames);
        
    }
    
    @FXML
    private void viewGamePressed() throws IOException {
        //Get the select game data and switch to GameView
        GameSearchData gameData = gameViewTable.getSelectionModel().getSelectedItem();
        FXGameViewController.setGameData(gameData);
        MainApp.setRoot("GameView");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        for (MenuItem menuItem : ratingMenu.getItems()) {
            menuItem.setOnAction(e -> {
                String newRating = menuItem.getText();
                ratingText.setText(newRating);
                if (newRating.equals("Any"))
                    esrbId = -1;
                else if (newRating.equals("NR"))
                    esrbId = 1;
                else if (newRating.equals("E"))
                    esrbId = 2;
                else if (newRating.equals("E10+"))
                    esrbId = 3;
                else if (newRating.equals("T"))
                    esrbId = 4;
                else if (newRating.equals("M"))
                    esrbId = 5;
                System.out.println(esrbId);
            });
        }
    }
}
