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

    private static GameData gameData;
    
    private GamePurchaseData newPurchase;
    
    public static HashMap<Long, Long> tempQuantity = new HashMap<>();
    public static HashMap<Long, ArrayList<UsedGame>> tempUsedGames = new HashMap<>(); //Left off here
    
    @FXML private Text nameText;
    @FXML private Text genreText;
    @FXML private Text publisherText;
    @FXML private Text ratingText;
    @FXML private Text newPriceText;
    @FXML private Text newQuantityText;
    
    @FXML private TableView<UsedGame> usedGameViewTable;
    @FXML private TableColumn<UsedGame, Double> usedPriceColumn;
    @FXML private TableColumn<UsedGame, String> usedConditionColumn;

    public static GameData getGameData() {
        return gameData;
    }
    
    public static void setGameData(GameData newData) {
        gameData = newData;
    }
    
    @FXML
    private void returnPressed() throws IOException {
        MainApp.setRoot("GameSearch");
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
    
    @FXML
    private void addUsedToCart() {
        UsedGame usedGame = usedGameViewTable.getSelectionModel().getSelectedItem();
        
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
        
        ArrayList<UsedGame> usedGames;
        
        if (tempUsedGames.containsKey(gameData.getGameId()) == false) {
            //Get array of usedGames for this gameId
            usedGames = UsedGameDAO.getUsedGamesFromGameData(gameData);
        
            for (UsedGame usedGame : usedGames) {
                tempUsedGames.put(gameData.getGameId(), usedGames);
            }
        }
        else {
            //Retreive list of used games for this gameId  
            usedGames = tempUsedGames.get(gameData.getGameId());
        }
        
        //Create setCellValueFactories and display the search results
        ObservableList<UsedGame> observableUsedGames = FXCollections.observableArrayList(usedGames);

        usedPriceColumn.setCellValueFactory(new PropertyValueFactory<UsedGame, Double>("price"));
        usedConditionColumn.setCellValueFactory(new PropertyValueFactory<UsedGame, String>("condition"));


        usedGameViewTable.setItems(observableUsedGames);
    }
}
