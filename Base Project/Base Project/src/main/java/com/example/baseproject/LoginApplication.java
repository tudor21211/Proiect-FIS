package com.example.baseproject;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.stage.StageStyle;


import java.io.IOException;

public class LoginApplication extends Application {
    private static Stage stg;
    @Override
        public void start(Stage primaryStage) throws IOException {
        stg = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        primaryStage.setTitle("Login");
        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(new Scene(root,520, 400));
        primaryStage.show();

    }
public void changeScene(String fxml) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource(fxml));
        stg.getScene().setRoot(pane);
}
    public static void main(String[] args) {
        launch(args);
    }
}