package com.mycompany.gamespotstoremanager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;

public class FXTransactionCompleteController extends FXController {

    @FXML
    private void switchToGameSearch() throws IOException {
        MainApp.setRoot("GameSearch");
    }
    
    @FXML
    private void switchToGameAdd() throws IOException {
        MainApp.setRoot("GameAdd");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
}
