package com.example.baseproject;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.File;
import java.io.IOException;
import java.net.PasswordAuthentication;
import java.sql.*;
import java.util.ResourceBundle;
import java.net.URL;



public class LoginController implements Initializable {
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
 @FXML
 private TextField userField;
 @FXML
 private PasswordField passField;
@FXML
private Label newRegistration;


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




        registerButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                if (!userField.getText().trim().isEmpty() && !passField.getText().trim().isEmpty()) {
                    DBUtils.signUpUser(userField.getText(),passField.getText(),0);
                    //newRegistration.setText("User "+userField.getText()+" registered succesfully");
                    invalidLogin.setText("");
                }else
                {
                    invalidLogin.setText("Fill all info to register");
                    System.out.println("Fill in all info");
                }
            }
        });
    }

    public void loginButtonOnAction() {
        if (!userField.getText().isBlank() && !passField.getText().isBlank()) {
            invalidLogin.setText("Check login...");
            newRegistration.setText("");
            validateLogin();
        }else
        {
            invalidLogin.setText("Enter user and password please...");
            newRegistration.setText("");
        }
    }


    public void cancelButtonOnAction () {

     Stage stage = (Stage) cancelButton.getScene().getWindow();
     stage.close();

    }

    public void validateLogin(){
            DBConnection connectNow = new DBConnection();
            Connection connectDB = connectNow.getConnection();

            String verifyLogin = "SELECT count(1) FROM user_account WHERE username ='" + userField.getText() + "' AND password ='" + passField.getText() + "'";
            try {

                Statement statement = connectDB.createStatement();
                ResultSet queryResult = statement.executeQuery(verifyLogin);


                while (queryResult.next()) {
                    if (queryResult.getInt(1)==1){
                        invalidLogin.setText("Welcome "+userField.getText());
                        newRegistration.setText("");
                    }else {
                        invalidLogin.setText("Invalid login credentials");
                        newRegistration.setText("");
                    }
                }

            }catch (Exception e){
                e.printStackTrace();
                e.getCause();
            }

    }





}