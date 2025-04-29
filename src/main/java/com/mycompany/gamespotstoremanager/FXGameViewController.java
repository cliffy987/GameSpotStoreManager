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

public class FXGameViewController extends FXController {

    private static GameSearchData gameData;
    
    private GamePurchaseData newPurchase;
    
    public static HashMap<Long, Long> tempQuantity = new HashMap<>();
    public static HashMap<Long, ArrayList<UsedGame>> tempUsedGames = new HashMap<>(); //Left off here
    
    @FXML private Text nameText;
    @FXML private Text genreText;
    @FXML private Text publisherText;
    @FXML private Text ratingText;
    @FXML private Text newPriceText;
    @FXML private Text newQuantityText;

    public static GameSearchData getGameData() {
        return gameData;
    }
    
    public static void setGameData(GameSearchData newData) {
        gameData = newData;
    }
    
    @FXML
    private void addNewToCart() {
        long quantity = tempQuantity.get(gameData.getGameId());
        if (quantity > 0) {
            tempQuantity.put(gameData.getGameId(), quantity - 1);
            FXCheckoutController.gamePurchases.add(newPurchase);
            newQuantityText.setText("New Quantity: " + (quantity - 1));
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Game is out of stock");
            alert.showAndWait();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        newPurchase = GameDAO.getNewGamePurchaseData(gameData);
        
        //Store quantity here rather than directly updating in database in
        //case program is closed before purchase/return
        if (tempQuantity.containsKey(gameData.getGameId()) == false)
            tempQuantity.put(gameData.getGameId(), newPurchase.getGameQuantity());
        
        nameText.setText("Name: " + gameData.getGameName());
        genreText.setText("Genre(s): " + gameData.getGameGenres());
        publisherText.setText("Publisher(s): " + gameData.getGamePublishers());
        ratingText.setText("Age Rating: " + gameData.getGameRating());
        newPriceText.setText("New Price: " + newPurchase.getGamePrice());
        newQuantityText.setText("New Quantity: " + tempQuantity.get(gameData.getGameId()));
    }
}
