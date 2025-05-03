/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gamespotstoremanager;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

/**
 *
 * @author ebwhi
 */
public abstract class FXController implements Initializable {
    @FXML
    private void switchToMainMenu() throws IOException {
        MainApp.setRoot("MainMenu");
    }
    
    @FXML
    private void switchToCheckout() throws IOException {
        MainApp.setRoot("Checkout");
    }
    
    @FXML
    public void standardError(String errorText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(errorText);
        alert.showAndWait();
    }

}
