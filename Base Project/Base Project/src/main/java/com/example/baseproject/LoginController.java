package com.example.baseproject;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.File;
import java.util.ResourceBundle;
import java.net.URL;

public class LoginController /*implements Initializable*/ {
    @FXML
    private Label welcomeText;

    private Button LoginButton;
 @FXML
    private Label invalidLogin;
 @FXML
 private ImageView brandingImageView;
 @FXML
 private ImageView lockImageView;
 @FXML
 private Button cancelButton;




 //@Override
 /*public void initialize (URL url, ResourceBundle resourceBundle) {
     File branding = new File("img.png");
     Image brandingImage = new Image(branding.toURI().toString());
     brandingImageView.setImage(brandingImage);

     File lock = new File("Lock.png");
     Image lockImage = new Image(lock.toURI().toString());
     lockImageView.setImage(lockImage);

 }
*/
    public void loginButtonOnAction(ActionEvent event) {
        invalidLogin.setText("You try to log in...");
    }

    public void cancelButtonOnAction (ActionEvent event) {
     Stage stage = (Stage) cancelButton.getScene().getWindow();
     stage.close();
    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}