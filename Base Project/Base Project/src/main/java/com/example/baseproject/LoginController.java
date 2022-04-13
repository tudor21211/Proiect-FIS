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

public class LoginController implements Initializable {
    @FXML
    private Label welcomeText;
@FXML
    private Button loginButton;
 @FXML
    private Label invalidLogin;
 @FXML
 private ImageView brandingImageView;
 @FXML
 private ImageView lockImageView;
 @FXML
 private Button cancelButton,logAdmButton;
 @FXML
 private Button registerButton;
    private static final String IDLE_BUTTON_STYLE = "-fx-background-color:  #323D42; -fx-text-fill: #FFFFFF;";
    private static final String IDLE_BUTTON_STYLE_Cancel = "-fx-background-color:   #FF0000; -fx-text-fill: #FFFFFF;";
    private static final String HOVERED_BUTTON_STYLE_Cancel = "-fx-background-color:#ff4000; -fx-shadow-highlight-color:#ff4000; -fx-text-fill: #FFFFFF;";
    private static final String HOVERED_BUTTON_STYLE = "-fx-background-color:#141f1f; -fx-shadow-highlight-color:#141f1f; -fx-text-fill: #FFFFFF;";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loginButton.setStyle(IDLE_BUTTON_STYLE);
        loginButton.setOnMouseEntered(e -> loginButton.setStyle(HOVERED_BUTTON_STYLE));
        loginButton.setOnMouseExited(e -> loginButton.setStyle(IDLE_BUTTON_STYLE));
        registerButton.setStyle(IDLE_BUTTON_STYLE);
        registerButton.setOnMouseEntered(e -> registerButton.setStyle(HOVERED_BUTTON_STYLE));
        registerButton.setOnMouseExited(e -> registerButton.setStyle(IDLE_BUTTON_STYLE));
        logAdmButton.setStyle(IDLE_BUTTON_STYLE);
        logAdmButton.setOnMouseEntered(e -> logAdmButton.setStyle(HOVERED_BUTTON_STYLE));
        logAdmButton.setOnMouseExited(e -> logAdmButton.setStyle(IDLE_BUTTON_STYLE));
        cancelButton.setStyle(IDLE_BUTTON_STYLE_Cancel);
        cancelButton.setOnMouseEntered(e -> cancelButton.setStyle(HOVERED_BUTTON_STYLE_Cancel));
        cancelButton.setOnMouseExited(e -> cancelButton.setStyle(IDLE_BUTTON_STYLE_Cancel));
    }

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