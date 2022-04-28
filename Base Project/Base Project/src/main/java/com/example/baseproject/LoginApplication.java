package com.example.baseproject;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.stage.StageStyle;


import java.io.IOException;

public class LoginApplication extends Application {
    private static Stage stg;
    private double xOffset = 0;
    private double yOffset = 0;

    @Override

    public void start(Stage primaryStage) throws IOException {
        stg = primaryStage;
        primaryStage.centerOnScreen();
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        primaryStage.setTitle("Login");
        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.UNDECORATED);


        primaryStage.setScene(new Scene(root, 520, 400));

        root.setOnMousePressed(new EventHandler < MouseEvent > () {
            @Override
            public void handle(MouseEvent event) {
                xOffset = primaryStage.getX() - event.getScreenX();
                yOffset = primaryStage.getY() - event.getScreenY();
            }
        });

        root.setOnMouseDragged(new EventHandler < MouseEvent > () {
            @Override
            public void handle(MouseEvent event) {
                primaryStage.setX(event.getScreenX() + xOffset);
                primaryStage.setY(event.getScreenY() + yOffset);
            }
        });
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}