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

public class FXGameUpdateController extends FXController{

    private static GameData gameData;
    private static GamePurchaseData purchaseData;
    
    private static ObservableList<Genre> gameGenres;
    private static ObservableList<Publisher> gamePublishers;
    private static ObservableList<UsedGame> usedCopies;
    
    @FXML private Text nameText;
    @FXML private Text ratingText;
    @FXML private Text newPriceText;
    @FXML private Text newQuantityText;
    @FXML private TextField newNameField;
    @FXML private TextField newPriceField;
    @FXML private TextField newQuantityField;
    @FXML private TextField newGenreField;
    @FXML private TextField newPublisherField;
    @FXML private TextField usedPriceField;
    @FXML private TextField usedQuantityField;
    @FXML private MenuButton ratingMenu;
    @FXML private MenuButton conditionMenu;
    int conditionId = -1;
    
    @FXML private TableView<Genre> genreViewTable;
    @FXML private TableColumn<Genre, String> genreNameColumn;
    
    @FXML private TableView<Publisher> publisherViewTable;
    @FXML private TableColumn<Publisher, String> publisherNameColumn;
    
    @FXML private TableView<UsedGame> usedGameViewTable;
    @FXML private TableColumn<UsedGame, Double> usedPriceColumn;
    @FXML private TableColumn<UsedGame, String> usedConditionColumn;
    
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
    private void addGenrePressed() {
        String genreName = newGenreField.getText();
        
        //Return if no match
        if (GenreDAO.genreNameExists(genreName) == false) {
            standardError("Genre does not exist.");
            return;
        }
        
        long genreId = GenreDAO.getIdFromGenre(genreName);
        
        //Return if already added
        for (Genre genre : gameGenres) {
            if (genre.getName().equals(genreName)) {
                standardError("Genre already added to this game.");
                return;
            }
        }
        
        //Add to database
        GenreDAO.addGenreToGame(gameData.getGameId(), genreId);
        Genre genre = GenreDAO.getGenreFromId(genreId);
        gameGenres.add(genre);
        gameData.setGameGenres(GenreDAO.getGenresStringForGameId(gameData.getGameId()));
    }
    
    @FXML
    private void addPublisherPressed() {
        String publisherName = newPublisherField.getText();
        
        //Return if no match
        if (PublisherDAO.publisherNameExists(publisherName) == false) {
            standardError("Publisher does not exist.");
            return;
        }
        
        long publisherId = PublisherDAO.getIdFromPublisher(publisherName);
        
        //Return if already added
        for (Publisher publisher : gamePublishers) {
            if (publisher.getName().equals(publisherName)) {
                standardError("Publisher already added to this game.");
                return;
            }
        }
        
        //Add to database
        PublisherDAO.addPublisherToGame(gameData.getGameId(), publisherId);
        Publisher publisher = PublisherDAO.getPublisherFromId(publisherId);
        gamePublishers.add(publisher);
        gameData.setGamePublishers(PublisherDAO.getPublishersStringForGameId(gameData.getGameId()));
    }
    
    @FXML
    private void addUsedPressed() {
        int quantity = 0;
        double price = 0;
        
        //Make sure condition has been selected
        if (conditionId == -1) {
            standardError("Please select a condition.");
            return;
        }
        
        //Make sure numbers are valid
        try {
            price = Double.parseDouble(usedPriceField.getText());
            quantity = Integer.parseInt(usedQuantityField.getText());
            if (price <= 0 || quantity <= 0)
                throw new NumberFormatException();
                
        } catch (Exception e) {
            standardError("Price and Number of Copies must both be numbers greater than 0.");
            return;
        }

        //Insert 
        for (int i = 0; i < quantity; i++) {
            String sql = "INSERT INTO used_game_stock (game_id, price, game_condition_id) VALUES " +
                    "(" + gameData.getGameId() + "," + price + "," + conditionId + ")";
            long usedId = DatabaseConnector.insertGetId(sql);
            System.out.println(usedId);
            UsedGame usedGame = new UsedGame(usedId, gameData, price, conditionMenu.getText());
            usedCopies.add(usedGame);
        }
    }
    
    @FXML
    private void removeGenrePressed() throws IOException {
        Genre genre = genreViewTable.getSelectionModel().getSelectedItem();
        long genreId = genre.getGenreId();
        GenreDAO.removeGenreFromGame(gameData.getGameId(), genreId);
        gameGenres.remove(genre);
        gameData.setGameGenres(GenreDAO.getGenresStringForGameId(gameData.getGameId()));
    }
    
    @FXML
    private void removePublisherPressed() throws IOException {
        Publisher publisher = publisherViewTable.getSelectionModel().getSelectedItem();
        long publisherId = publisher.getPublisherId();
        PublisherDAO.removePublisherFromGame(gameData.getGameId(), publisherId);
        gamePublishers.remove(publisher);
        gameData.setGamePublishers(PublisherDAO.getPublishersStringForGameId(gameData.getGameId()));
    }
    
    @FXML
    private void removeUsedPressed() throws IOException {
        UsedGame copy = usedGameViewTable.getSelectionModel().getSelectedItem();
        long usedId = copy.getUsedId();
        UsedGameDAO.removeUsedGame(usedId);
        usedCopies.remove(copy);
    }
    
    @FXML
    private void updateNamePressed() throws IOException {
        String newName = newNameField.getText();
        GameDAO.updateGameName(gameData.getGameId(), newName);
        gameData.setGameName(newName);
        nameText.setText("Name: " + newName);
    }
    
    @FXML
    private void updatePricePressed() throws IOException {
        double price = 0;
        try {
            price = Double.parseDouble(newPriceField.getText());
            if (price <= 0)
                throw new NumberFormatException();
                
        } catch (Exception e) {
            standardError("Price must be a number greater than 0.");
            return;
        }
        
        GameDAO.updateGamePrice(gameData.getGameId(), price);
        gameData.set //Left off here; set price object's price
        nameText.setText("Name: " + newName);
    }
    
    @FXML
    private void returnPressed() throws IOException {
        MainApp.setRoot("GameView");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nameText.setText("Name: " + gameData.getGameName());
        ratingText.setText("Age Rating: " + gameData.getGameRating());
        newPriceText.setText("New-Copy Price: $" + purchaseData.getGamePrice());
        newQuantityText.setText("New-Copy Quantity: " + purchaseData.getGameQuantity());
        
        genreNameColumn.setCellValueFactory(new PropertyValueFactory<Genre, String>("name"));
        genreViewTable.setItems(gameGenres);
        
        publisherNameColumn.setCellValueFactory(new PropertyValueFactory<Publisher, String>("name"));
        publisherViewTable.setItems(gamePublishers);
        
        usedPriceColumn.setCellValueFactory(new PropertyValueFactory<UsedGame, Double>("price"));
        usedConditionColumn.setCellValueFactory(new PropertyValueFactory<UsedGame, String>("condition"));
        usedGameViewTable.setItems(usedCopies);
        
        //Setup esrb
        for (MenuItem menuItem : ratingMenu.getItems()) {
            menuItem.setOnAction(e -> {
                String newRating = menuItem.getText();
                ratingText.setText("Age Rating: " + newRating); 
                gameData.setGameRating(newRating);
                if (newRating.equals("NR"))
                    GameDAO.updateGameESRB(gameData.getGameId(), 1);
                else if (newRating.equals("E"))
                    GameDAO.updateGameESRB(gameData.getGameId(), 2);
                else if (newRating.equals("E10+"))
                    GameDAO.updateGameESRB(gameData.getGameId(), 3);
                else if (newRating.equals("T"))
                    GameDAO.updateGameESRB(gameData.getGameId(), 4);
                else if (newRating.equals("M"))
                    GameDAO.updateGameESRB(gameData.getGameId(), 5);
            });
        }
        
        //Setup condition menu
        for (MenuItem menuItem : conditionMenu.getItems()) {
            menuItem.setOnAction(e -> {
                String condition = menuItem.getText();
                conditionMenu.setText(condition);
                if (conditionMenu.getText().equals("VERY POOR"))
                    conditionId = 1;
                else if (conditionMenu.getText().equals("POOR"))
                    conditionId = 2;
                else if (conditionMenu.getText().equals("OKAY"))
                    conditionId = 3;
                else if (conditionMenu.getText().equals("GOOD"))
                    conditionId = 4;
                else if (conditionMenu.getText().equals("GREAT"))
                    conditionId = 5;
                else if (conditionMenu.getText().equals("EXCELLENT"))
                    conditionId = 6;
                else
                    conditionId = -1;
                System.out.println(conditionId);
            });
        }
        
    }
}
