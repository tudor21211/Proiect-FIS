package com.example.baseproject;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;

public class HistoryViewAdminController {

    @FXML
    private Accordion accordion;
    @FXML
    private Button cancelButton;
    private double xOffset = 0;
    private double yOffset = 0;

    public void cancelButtonOnAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/baseproject/admin_view.fxml"));
            Parent root = (Parent) loader.load();
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            AdminViewController viewController = loader.getController();//de linia asta
            stage.close();
            viewController.update();
            stage = new Stage();
            stage.setScene(new Scene(root,1250,850));
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
        }catch (IOException e) {
            e.printStackTrace();
        }

    }



    public void updateAdmin()
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
                pane.setMinWidth(400);
                pane.setText(queryResult1.getString("team1") + " vs "+queryResult1.getString("team2"));
                //Image image  = new Image(getClass().getResourceAsStream("FAZE.png"));

                Image image = new Image(new FileInputStream(queryResult1.getString("team2")+".jpg"));
                ImageView imageView = new ImageView(image);
                imageView.setFitHeight(100.0);
                imageView.setFitWidth(100.0);
                imageView.setX(320);
                imageView.setY(0);
                image = new Image(new FileInputStream(queryResult1.getString("team1")+".jpg"));
                ImageView imageView2 = new ImageView(image);
                imageView2.setFitHeight(100.0);
                imageView2.setFitWidth(100.0);
                imageView2.setX(20);

                Label chance1 = new Label("Chances: "+queryResult1.getString("odd1"));
                chance1.setLayoutX(140);
                chance1.setLayoutY(50);
                Label chance2 = new Label("Chances: "+queryResult1.getString("odd2"));
                chance2.setLayoutX(240);
                chance2.setLayoutY(50);

                Label date = new Label("Start time: "+queryResult1.getString("start"));
                date.setLayoutX(140);
                date.setLayoutY(100);


                AnchorPane anchorpane = new AnchorPane();
                anchorpane.getChildren().add(imageView);
                anchorpane.getChildren().add(imageView2);
                anchorpane.getChildren().add(chance1);
                anchorpane.getChildren().add(chance2);
                anchorpane.getChildren().add(date);
                pane.setContent(anchorpane);

                pane.setCollapsible(true);
                accordion.setExpandedPane(pane);
                accordion.getPanes().add(pane);

            }
            System.out.println(accordion.getPanes().size());

            accordion.setVisible(true);

        }catch(SQLException e)
        {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
