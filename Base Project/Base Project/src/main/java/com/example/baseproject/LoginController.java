package com.example.baseproject;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.File;
import java.io.IOException;
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
 private TextField passField;
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
    }

    public void loginButtonOnAction() {


        if (!userField.getText().isBlank() && !passField.getText().isBlank()) {
            invalidLogin.setText("Check login...");
            validateLogin();
        }else
        {
            invalidLogin.setText("Enter user and password please...");
        }
    }

    public void registrationButtonOnAction(){
        if (!userField.getText().isBlank() && !passField.getText().isBlank()) {
            newRegistration.setText("Try again to register...");
            //signUpUser(userField.getText(),passField.getText(),0);
        }else
        {
            newRegistration.setText("Enter user and password please...");
        }
    }


    public void cancelButtonOnAction () {

     Stage stage = (Stage) cancelButton.getScene().getWindow();
     stage.close();
    }

    /*public void newRegister(){
        DBConnection connectNow = new DBConnection();
        Connection connectDB = connectNow.getConnection();
        String registration = "INSERT INTO user_account (username, password, balance) VALUES " + "(" + "'"+ userField.getText()+"'"+ "," + "'"+ passField.getText()+"'" + "," + 0+")";

        try {

            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(registration);


            while (queryResult.next()) {
                if (queryResult.getInt(1)==1){
                    newRegistration.setText("Welcome new registered user "+userField.getText());
                }else {
                    newRegistration.setText("Try again to register...");
                }
            }

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }*/


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
                    }else {
                        invalidLogin.setText("Invalid login credentials");
                    }
                }

            }catch (Exception e){
                e.printStackTrace();
                e.getCause();
            }

    }


    public static void signUpUser(String username, String password, int  balance) {
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckUsername = null;
        ResultSet resultSet = null;
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost/usersdb","root","parolaUsersDB");
            psCheckUsername = connection.prepareStatement("SELECT * FROM user_account WHERE username = ?");
            psCheckUsername.setString(1,username);
            resultSet = psCheckUsername.executeQuery();

            if (resultSet.isBeforeFirst()){
                System.out.println("User already exists");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Username already taken");
                alert.show();
            }
            else {
                psInsert = connection.prepareStatement("Insert INTO user_account(username, password, balance) VALUES (?,?,?)");
                psInsert.setString(1,username);
                psInsert.setString(2,password);
                psInsert.setInt(3,balance);
                psInsert.executeQuery();

            }
        }catch (SQLException e) {
            e.printStackTrace();
            e.getCause();
        }
        finally {
            if (resultSet != null){
                try {
                    resultSet.close();
                }catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psCheckUsername != null) {
                try {
                    psCheckUsername.close();
                }catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psInsert != null) {
                try {
                    psInsert.close();
                }catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                }catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}