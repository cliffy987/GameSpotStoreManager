package com.mycompany.gamespotstoremanager;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

public class FXGameAddController extends FXController{
    
    private static ArrayList<Genre> gameGenres;
    private static ArrayList<Publisher> gamePublishers;
    
    private static ObservableList<Genre> gameObsGenres;
    private static ObservableList<Publisher> gameObsPublishers;
    
    private static int esrbId = -1;
    private static String esrb = "Select";
    
    @FXML private Text nameText;
    @FXML private Text ratingText;
    @FXML private Text newPriceText;
    @FXML private Text newQuantityText;
    @FXML private TextField newNameField;
    @FXML private TextField newPriceField;
    @FXML private TextField newQuantityField;
    @FXML private TextField newGenreField;
    @FXML private TextField newPublisherField;
    @FXML private MenuButton ratingMenu;
    @FXML private DatePicker releaseDatePicker;
    int conditionId = -1;
    
    @FXML private TableView<Genre> genreViewTable;
    @FXML private TableColumn<Genre, String> genreNameColumn;
    
    @FXML private TableView<Publisher> publisherViewTable;
    @FXML private TableColumn<Publisher, String> publisherNameColumn;
    
    private void updateGenreTable() {
        ObservableList<Genre> obsGameGenres = FXCollections.observableArrayList(gameGenres);
        genreNameColumn.setCellValueFactory(new PropertyValueFactory<Genre, String>("name"));
        genreViewTable.setItems(obsGameGenres);
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
        
        //Add to Genre List
        Genre genre = GenreDAO.getGenreFromId(genreId);
        gameGenres.add(genre);
        updateGenreTable();
    }
    
    @FXML
    private void removeGenrePressed() throws IOException {
        Genre genre = genreViewTable.getSelectionModel().getSelectedItem();
        gameGenres.remove(genre);
        updateGenreTable();
    }
    
    private void updatePublisherTable() {
        ObservableList<Publisher> obsGamePublishers = FXCollections.observableArrayList(gamePublishers);
        publisherNameColumn.setCellValueFactory(new PropertyValueFactory<Publisher, String>("name"));
        publisherViewTable.setItems(obsGamePublishers);
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
        
        //Add to list
        Publisher publisher = PublisherDAO.getPublisherFromId(publisherId);
        gamePublishers.add(publisher);
        updatePublisherTable();
    }

    @FXML
    private void removePublisherPressed() throws IOException {
        Publisher publisher = publisherViewTable.getSelectionModel().getSelectedItem();
        gamePublishers.remove(publisher);
        updatePublisherTable();
    }
    
    @FXML
    private void confirmAddNewGamePressed() throws IOException {
        //Name
        String newName = newNameField.getText();
        
        //Don't want blank names
        if (newName.isBlank()) {
            standardError("Please enter a valid name.");
            return;
        }
        
        //Release Date
        ZoneId systemZone = ZoneId.systemDefault();
        LocalDate localDate = releaseDatePicker.getValue();
        
        if (localDate == null) {
            standardError("Please select a release date.");
            return;
        }
        
        Date releaseDate = Date.valueOf(localDate);
        
        //ESRB
        if (esrb.equals("Select") || esrbId == -1) {
            standardError("Please select an ESRB rating.");
            return;
        }
        
        //Price
        double price = 0;
        try {
            price = Double.parseDouble(newPriceField.getText());
            if (price <= 0)
                throw new NumberFormatException();
                
        } catch (Exception e) {
            standardError("Price must be a number greater than 0.");
            return;
        }
        
        //Quantity
        long quantity = 0;
        try {
            quantity = Long.parseLong(newQuantityField.getText());
                
        } catch (Exception e) {
            standardError("Quantity must be an integer value.");
            return;
        }
        
        //public Game(long gameId, String gameName, Date releaseDate, int esrbId, String esrb, ArrayList<Genre> genreList, ArrayList<Publisher> publisherList) {
        Game game = new Game(-1, newName, releaseDate, esrbId, esrb, gameGenres, gamePublishers);
        GameDAO.addGameToDatabase(game, price, quantity);
        long gameId = game.getGameId();
        System.out.println("New game ID: " + gameId);
        GameData gameData = new GameData(gameId, game.getGameName(), GenreDAO.getGenresStringForGameId(gameId), PublisherDAO.getPublishersStringForGameId(gameId), esrb);
        FXGameViewController.setGameData(gameData);
        MainApp.setRoot("GameView");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Reset lists
        gameGenres = new ArrayList<>();
        gamePublishers = new ArrayList<>();
        
        //Setup esrb
        esrbId = -1;
        esrb = "Select";
        for (MenuItem menuItem : ratingMenu.getItems()) {
            menuItem.setOnAction(e -> {
                String newRating = menuItem.getText();
                ratingMenu.setText(newRating);
                esrb = newRating;
                if (newRating.equals("NR"))
                    esrbId = 1;
                else if (newRating.equals("E"))
                    esrbId = 2;
                else if (newRating.equals("E10+"))
                    esrbId = 3;
                else if (newRating.equals("T"))
                    esrbId = 4;
                else if (newRating.equals("M"))
                    esrbId = 5;
            });
        }
        
        
    }
}
