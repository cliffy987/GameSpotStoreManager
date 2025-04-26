package com.mycompany.gamespotstoremanager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class FXGameSearchController implements Initializable {

    @FXML private TextField nameField;
    @FXML private TextField genreField;
    @FXML private TextField publisherField;
    @FXML private MenuButton ratingMenu;
    @FXML private Text ratingText;
    
    private String nameEntered = "";
    private String genreEntered = "";
    private String publisherEntered = "";
    
    @FXML
    private void switchToMainMenu() throws IOException {
        MainApp.setRoot("MainMenu");
    }
    
    @FXML
    private void nameFieldUpdated() {
        nameEntered = nameField.getText();
    }
    
    @FXML
    private void genreFieldUpdated() {
        genreEntered = genreField.getText();
    }
    
    @FXML
    private void publisherFieldUpdated() {
        publisherEntered = publisherField.getText();
    }
    
    private void searchPressed() {
        //Just in case user didn't press "Enter"
        nameFieldUpdated();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        for (MenuItem menuItem : ratingMenu.getItems()) {
            menuItem.setOnAction(e -> {
                ratingText.setText(menuItem.getText());
            });
        }
    }
}
