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
import javafx.scene.layout.AnchorPane;
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

    private static Stage stage;
    Scene scene;
    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    public Accordion accordion;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    public Label testLabel;

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
            Stage finalStage1 = stage;
            root.setOnMousePressed(new EventHandler <MouseEvent> () {
                @Override
                public void handle(MouseEvent event) {
                    xOffset = finalStage1.getX() - event.getScreenX();
                    yOffset = finalStage1.getY() - event.getScreenY();
                }
            });

            Stage finalStage = stage;
            root.setOnMouseDragged(new EventHandler < MouseEvent > () {
                @Override
                public void handle(MouseEvent event) {
                    finalStage.setX(event.getScreenX() + xOffset);
                    finalStage.setY(event.getScreenY() + yOffset);
                }
            });
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

    public void update()
    {
        DBConnection connectNow = new DBConnection();
        Connection connectDB = connectNow.getConnection();
        PreparedStatement psInsert = null;
        String getMatch = "SELECT * FROM matches WHERE start<now()";

        Accordion acc = new Accordion();
        try{
            Statement statement1 = connectDB.createStatement();
            ResultSet queryResult1 = statement1.executeQuery(getMatch);
            while(queryResult1.next()) {
                System.out.println(queryResult1.getString("team1"));
                System.out.println(queryResult1.getString("team2"));
                TitledPane pane = new TitledPane();
                pane.setText("match");
                pane.setContent(new Button("CACAT"));
                //pane.setExpanded(true);
                pane.setCollapsible(true);
                accordion.setExpandedPane(pane);
                accordion.getPanes().add(pane);

            }
        System.out.println(accordion.getPanes().size());
           // System.out.println(accordion);
            //accordion=acc;

            accordion.setVisible(true);

        }catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

}
