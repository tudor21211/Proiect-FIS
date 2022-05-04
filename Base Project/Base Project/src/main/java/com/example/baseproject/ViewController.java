package com.example.baseproject;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.File;
import java.io.IOException;
import java.net.PasswordAuthentication;
import java.sql.*;
import java.util.ResourceBundle;
import java.net.URL;


public class ViewController extends LoginController {

    @FXML
    private Button testButton;
    @FXML
    private Button logoutButton;
    @FXML
    public Label usernameField;
    @FXML
    public Label balanceField;

    private Stage stage;
    private Scene scene;
    private double xOffset = 0;
    private double yOffset = 0;
private Accordion accordion;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


        public void logoutButtonOnAction(ActionEvent event) {
            try {

             // LoginApplication.changeScene1("login.fxml");

                Parent root = FXMLLoader.load(getClass().getResource("/com/example/baseproject/login.fxml"));
               stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
               scene = new Scene (root);
               stage.setScene(scene);
               stage.centerOnScreen();
                root.setOnMousePressed(new EventHandler <MouseEvent> () {
                    @Override
                    public void handle(MouseEvent event) {
                        xOffset = stage.getX() - event.getScreenX();
                        yOffset = stage.getY() - event.getScreenY();
                    }
                });

                root.setOnMouseDragged(new EventHandler < MouseEvent > () {
                    @Override
                    public void handle(MouseEvent event) {
                        stage.setX(event.getScreenX() + xOffset);
                        stage.setY(event.getScreenY() + yOffset);
                    }
                });
               stage.show();


            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    public void setUsernameField(String username) {

        usernameField.setText(username);

    }

    public void setBalanceField () {
        DBConnection connectNow = new DBConnection();
        Connection connectDB = connectNow.getConnection();
        //System.out.println(usernameField);
        String getBalance = "SELECT balance from user_account where username='"+usernameField.getText()+"'";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet bal = statement.executeQuery(getBalance);
            //balanceField.setText(bal.getString("balance"));
            while (bal.next()) {
                String balance = bal.getString("balance");
                balanceField.setText(balance);
                System.out.println("Balance:"+balance);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }


    }

}

