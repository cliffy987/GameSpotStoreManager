package com.mycompany.gamespotstoremanager;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

public class FXCheckoutController extends FXController {
    
    @FXML private TableView<GamePurchaseData> gameViewTable;
    @FXML private TableColumn<GamePurchaseData, String> gameNameColumn;
    @FXML private TableColumn<GamePurchaseData, Double> gamePriceColumn;
    @FXML private TableColumn<GamePurchaseData, String> gameConditionColumn;
    
    @FXML private CheckBox paidWithCash;
    
    @FXML private TextField cardNumberField;
    @FXML private TextField cardCCVField;
    @FXML private MenuButton cardMonthMenu;
    private String cardMonth;
    @FXML private TextField cardYearField;
    
    @FXML private Text totalPriceText;

    public static ArrayList<GamePurchaseData> gamePurchases = new ArrayList<GamePurchaseData>();
    private static double totalPrice = 0;
    
    //"^" denotes that there must be nothing preceding the string
    //double "\\" is necessary to denote a single "\" which is needed for
    //the "\d{4}" command, which requires a series of exactly four digits 0-9.
    //"-" represents a literal "-" character.
    //"$" denotes that there must be nothing after the string.
    private static final Pattern CREDIT_CARD_PATTERN = Pattern.compile("^\\d{4}-\\d{4}-\\d{4}-\\d{4}$");
    
    //3 digits, 0-9. No more, no less.
    private static final Pattern CCV_PATTERN = Pattern.compile("^\\d{3}$");
    
    @FXML
    private void removeFromCartPressed() throws IOException {
        GamePurchaseData data = gameViewTable.getSelectionModel().getSelectedItem();
        gamePurchases.remove(data);
        updateCart();
        
        if (data.getUsedId() == -1) {
            //Update temp new-copy quantity
            FXGameViewController.incrementTempQuantity(data.getGameId(), 1);
        } else {
            //Re-add temp used game
            UsedGame usedGame = new UsedGame(data.getUsedId(), data.getGameData(), data.getGamePrice(), data.getCondition());
            FXGameViewController.addTempUsedGame(data.getGameId(), usedGame);
        }
       
    }
    
    @FXML
    private void purchasePressed() throws IOException {
        
        //Pay with card
        if (paidWithCash.isSelected() == false) {
            String cardNumber = this.cardNumberField.getText();
            //Make sure card is in valid format
            if (CREDIT_CARD_PATTERN.matcher(cardNumber).matches() == false) {
                standardError("Card number invalid.");
                return;
            }

            //Make sure CCV is valid
            String cardCCV = this.cardCCVField.getText();
            if (CCV_PATTERN.matcher(cardCCV).matches() == false) {
                standardError("Card CCV invalid.");
                return;
            }

            //Make sure month has been selected
            if (cardMonth == null) {
                standardError("Please select a card expiration month.");
                return;
            }
            
            //Make sure year is valid
            int cardYear = 0;
            try {
                cardYear = Integer.parseInt(cardYearField.getText());
            } catch (Exception e) {
                standardError("Card expiration year is invalid.");
                return;
            }
            
            if (CardReaderManager.performCardTransaction(cardNumber, cardCCV, cardMonth, cardYear) == false) {
                standardError("Card declined.");
                return;
            }
        }
        
        //Successful purchase; remove games from database
        for (GamePurchaseData gameData : gamePurchases) {
            //Left off here; Add purchase records for both new and used
            
            int conditionId = UsedGameDAO.getConditionIdFromString(gameData.getCondition());
            //Timestamp not necessary because its automatically added
            
            String recSql = "INSERT INTO purchases (game_id, price, game_condition_id) VALUES("+gameData.getGameId()+","+gameData.getGamePrice()+","+conditionId+")";
            DatabaseConnector.insertGetId(recSql);
            
            if (gameData.getUsedId() > 0) {
                //Used
                UsedGameDAO.removeUsedGame(gameData.getUsedId());
            } else {
                //New
                GameDAO.adjustNewQuantity(gameData.getGameId(), -1);
            }
        }
        
        gamePurchases.clear();
        MainApp.setRoot("TransactionComplete");
    }
    
    private void updateCart() {
        ObservableList<GamePurchaseData> observableGames = FXCollections.observableArrayList(gamePurchases);
        
        gameNameColumn.setCellValueFactory(new PropertyValueFactory<GamePurchaseData, String>("gameName"));
        gamePriceColumn.setCellValueFactory(new PropertyValueFactory<GamePurchaseData, Double>("gamePrice"));
        gameConditionColumn.setCellValueFactory(new PropertyValueFactory<GamePurchaseData, String>("condition"));

        gameViewTable.setItems(observableGames);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Get total
        totalPrice = 0;
        for (GamePurchaseData purchase : gamePurchases) {
            totalPrice += purchase.getGamePrice();
        }
        NumberFormat costFormat = NumberFormat.getCurrencyInstance(Locale.US);
        String costString = costFormat.format(totalPrice);
        totalPriceText.setText("Total: " + costString);
        
        //Set up expiration month menu
        for (MenuItem menuItem : cardMonthMenu.getItems()) {
            menuItem.setOnAction(e -> {
                cardMonth = menuItem.getText();
                cardMonthMenu.setText(cardMonth);
            });
        }
       
        //Set up game list
        updateCart();
        
    }
}
