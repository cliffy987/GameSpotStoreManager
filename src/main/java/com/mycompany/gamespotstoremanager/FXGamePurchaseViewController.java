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

public class FXGamePurchaseViewController extends FXController {

    @FXML private TableView<PurchaseRecord> purchaseViewTable;
    @FXML private TableColumn<PurchaseRecord, String> nameColumn;
    @FXML private TableColumn<PurchaseRecord, Double> priceColumn;
    @FXML private TableColumn<PurchaseRecord, String> conditionColumn;
    @FXML private TableColumn<PurchaseRecord, Timestamp> timestampColumn;
    
    private String nameEntered = "";
    private String genreEntered = "";
    private String publisherEntered = "";
    private int esrbId = -1;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<PurchaseRecord> obsRecords = FXCollections.observableArrayList(PurchaseRecordDAO.getAllPurchaseRecords());
        
        nameColumn.setCellValueFactory(new PropertyValueFactory<PurchaseRecord, String>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<PurchaseRecord, Double>("price"));
        conditionColumn.setCellValueFactory(new PropertyValueFactory<PurchaseRecord, String>("condition"));
        timestampColumn.setCellValueFactory(new PropertyValueFactory<PurchaseRecord, Timestamp>("timestamp"));

        purchaseViewTable.setItems(obsRecords);
    }
}
