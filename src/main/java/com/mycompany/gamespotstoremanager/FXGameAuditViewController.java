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

public class FXGameAuditViewController extends FXController {

    @FXML private TableView<GameAuditData> purchaseViewTable;
    @FXML private TableColumn<GameAuditData, String> nameColumn;
    @FXML private TableColumn<GameAuditData, String> descriptionColumn;
    @FXML private TableColumn<GameAuditData, Timestamp> timestampColumn;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<GameAuditData> obsAudit = FXCollections.observableArrayList(GameAuditDataDAO.getAllGameAudits());
        
        nameColumn.setCellValueFactory(new PropertyValueFactory<GameAuditData, String>("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<GameAuditData, String>("desc"));
        timestampColumn.setCellValueFactory(new PropertyValueFactory<GameAuditData, Timestamp>("timestamp"));

        purchaseViewTable.setItems(obsAudit);
    }
}
