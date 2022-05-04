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
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.File;
import java.io.IOException;
import java.net.PasswordAuthentication;
import java.sql.*;
import java.util.ResourceBundle;
import java.net.URL;
import javafx.scene.control.Button;
import javafx.stage.StageStyle;

public class AdminViewController {
    @FXML
    private Button addMatchButton;

    @FXML
    private Button logoutButton;

    Stage stage;
    Scene scene;


    public void addMatchOnAction(ActionEvent event)
    {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/baseproject/add_match_view.fxml"));
            Parent root = (Parent) loader.load();
            Stage stage = (Stage) addMatchButton.getScene().getWindow();
            stage.close();
            stage = new Stage();
            stage.setScene(new Scene(root,600,400));
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
            //AddMatchController.initialize();
        }catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("great success");
    }

    public void logoutButtonOnAction(ActionEvent event)
    {
        try {

            // LoginApplication.changeScene1("login.fxml");

            Parent root = FXMLLoader.load(getClass().getResource("/com/example/baseproject/login.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene (root);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
