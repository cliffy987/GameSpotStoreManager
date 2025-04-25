package com.mycompany.gamespotstoremanager;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class FXGameSearchController {

    @FXML private TextField nameField;
    
    private String nameEntered = "";
    
    @FXML
    private void switchToMainMenu() throws IOException {
        MainApp.setRoot("MainMenu");
    }
    
    @FXML
    private void nameFieldUpdated() {
        nameEntered = nameField.getText();
        System.out.println(nameEntered);
    }
    
    private void searchPressed() {
        //Just in case user didn't press "Enter"
        nameFieldUpdated();
    }
}
