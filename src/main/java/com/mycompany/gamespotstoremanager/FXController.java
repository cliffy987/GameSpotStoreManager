/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gamespotstoremanager;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 *
 * @author ebwhi
 */
public abstract class FXController implements Initializable {
    @FXML
    private void switchToMainMenu() throws IOException {
        MainApp.setRoot("MainMenu");
    }
}
