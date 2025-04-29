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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
}
