package com.mycompany.gamespotstoremanager;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

public class FXGameUpdateController extends FXController {

    private static Game game;
    private static GameData gameData;
    private static GamePurchaseData purchaseData;
    
    private ArrayList<Genre> gameGenres;
    private ArrayList<Publisher> gamePublishers;
    
    @FXML private Text nameText;
    @FXML private Text genreText;
    @FXML private Text newPriceText;
    @FXML private Text newQuantityText;
    
    @FXML private TableView<Genre> genreViewTable;
    @FXML private TableColumn<Genre, String> genreNameColumn;
    
    @FXML private TableView<Genre> publisherViewTable;
    @FXML private TableColumn<Publisher, String> publisherNameColumn;

    //RULE: YOU CANNOT ACCESS THIS MENU IF THERE ARE ITEMS IN THE CART
    
    //Updating name; if there's an SQL exception, that means the name is the
    //same as another game.
    //Updating publisher; select from a list to remove, or input name of
    //publisher to add. Come up with an error if the publisher name does not
    //exist.
    //Updating genre; same as publisher
    //Updating rating; select from dropdown
    
    //Name and ESRB will be changed with a confirm button next to each of them,
    //Genre and Publisher changes happen immeditately.
    
    public static GameData getGameData() {
        return gameData;
    }
    
    public static void setGameData(GameData newData) {
        gameData = newData;
    }
    
    @FXML
    private void addUsedEntry() throws IOException {
        MainApp.setRoot("GameSearch");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nameText.setText("Name: " + gameData.getGameName());
        genreText.setText("Genre(s): " + gameData.getGameGenres());
        newPriceText.setText("New Price: " + purchaseData.getGamePrice());
        newQuantityText.setText("New Quantity: " + purchaseData.getGameQuantity());
        
        gameGenres = GenreDAO.getAllGenresForGameId(gameData.getGameId());
        gamePublishers = PublisherDAO.getAllPublishersForGameId(gameData.getGameId());
    }
}
