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

    private static GameData gameData;
    private static GamePurchaseData purchaseData;
    
    private static ObservableList<Genre> gameGenres;
    private static ObservableList<Publisher> gamePublishers;
    private static ObservableList<UsedGame> usedCopies;
    
    @FXML private Text nameText;
    @FXML private Text ratingText;
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
    
    public static void setPurchaseData(GamePurchaseData newData) {
        purchaseData = newData;
    }
    
    public static void setGameGenres(ObservableList<Genre> genreList) {
        gameGenres = genreList;
    }
    
    public static void setGamePublishers(ObservableList<Publisher> publisherList) {
        gamePublishers = publisherList;
    }
    
    public static void setUsedCopied(ObservableList<UsedGame> usedGamesList) {
        usedCopies = usedGamesList;
    }
    
    @FXML
    private void addUsedEntry() throws IOException {
        MainApp.setRoot("GameSearch");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nameText.setText("Name: " + gameData.getGameName());
        ratingText.setText("Age Rating: " + gameData.getGameGenres());
        newPriceText.setText("New-Copy Price: $" + purchaseData.getGamePrice());
        newQuantityText.setText("New-Copy Quantity: " + purchaseData.getGameQuantity());
        
        genreNameColumn.setCellValueFactory(new PropertyValueFactory<Genre, String>("name"));
        genreViewTable.setItems(gameGenres);
    }
}
