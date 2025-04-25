package com.mycompany.gamespotstoremanager;

import java.io.IOException;
import javafx.fxml.FXML;

public class FXMainMenuController {

    @FXML
    private void switchToGameSearch() throws IOException {
        MainApp.setRoot("GameSearch");
    }
}
