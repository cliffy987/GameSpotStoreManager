package com.mycompany.gamespotstoremanager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;

public class FXMainMenuController extends FXController {

    @FXML
    private void switchToGameSearch() throws IOException {
        MainApp.setRoot("GameSearch");
    }
    
    @FXML
    private void switchToGameAdd() throws IOException {
        MainApp.setRoot("GameAdd");
    }
    
    @FXML
    private void switchToViewPurchases() throws IOException {
        MainApp.setRoot("GamePurchaseView");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
}
