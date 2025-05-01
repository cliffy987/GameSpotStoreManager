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
    public static HashMap<Long, ObservableList<UsedGame>> tempUsedGames = new HashMap<>(); //Left off here
    
    ObservableList<UsedGame> observableUsedGames;
    
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
    private void updatePressed() throws IOException {
        if (FXCheckoutController.gamePurchases.size() == 0) {
            FXGameUpdateController.setGameData(gameData);
            FXGameUpdateController.setPurchaseData(newPurchase); //New-copy Quantity will be same as databse since cart is empty
            FXGameUpdateController.setUsedCopied(observableUsedGames);
            
            //Create observable list for genres
            ArrayList<Genre> genreList = GenreDAO.getAllGenresForGameId(gameData.getGameId());
            ObservableList<Genre> genreObsList = FXCollections.observableArrayList(genreList);
            ArrayList<Publisher> publisherList = PublisherDAO.getAllPublishersForGameId(gameData.getGameId());
            ObservableList<Publisher> publisherObsList = FXCollections.observableArrayList(publisherList);
            
            FXGameUpdateController.setGameGenres(genreObsList);
            FXGameUpdateController.setGamePublishers(publisherObsList);

            MainApp.setRoot("GameUpdate");
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Cart must be empty to update game data.");
            alert.showAndWait();
        }
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
            alert.setContentText("Game is out of stock.");
            alert.showAndWait();
        }
    }
    
    @FXML
    private void addUsedToCart() {
        if (usedGameViewTable.getSelectionModel().getSelectedItem() != null) {
            UsedGame usedGame = usedGameViewTable.getSelectionModel().getSelectedItem();
            GamePurchaseData usedGamePurchase = new GamePurchaseData(gameData, usedGame.getPrice(), -1, usedGame.getUsedId());
            FXCheckoutController.gamePurchases.add(usedGamePurchase);
            observableUsedGames.remove(usedGame);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please make a selection first.");
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
        
        
        if (tempUsedGames.containsKey(gameData.getGameId()) == false) {
            //Get array of usedGames for this gameId
            observableUsedGames = FXCollections.observableArrayList(UsedGameDAO.getUsedGamesFromGameData(gameData));
            tempUsedGames.put(gameData.getGameId(), observableUsedGames);
        }
        else {
            //Retreive list of used games for this gameId  
            observableUsedGames = tempUsedGames.get(gameData.getGameId());
        }

        usedPriceColumn.setCellValueFactory(new PropertyValueFactory<UsedGame, Double>("price"));
        usedConditionColumn.setCellValueFactory(new PropertyValueFactory<UsedGame, String>("condition"));

        usedGameViewTable.setItems(observableUsedGames);
    }
}
