package com.example.baseproject;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.PasswordAuthentication;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.ResourceBundle;
import java.net.URL;
import javafx.scene.control.Button;
import javafx.stage.StageStyle;
import javafx.scene.image.Image;
import org.controlsfx.control.spreadsheet.Grid;

public class AdminViewController {
    @FXML
    private Button addMatchButton;

    @FXML
    private Button logoutButton;
    @FXML
    private Button historyButton;
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
        String getMatch = "SELECT * FROM matches WHERE start>now()";

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
                chance1.setLayoutX(121);
                chance1.setLayoutY(50);
                chance1.setStyle("-fx-text-fill:BLACK;-fx-font-weight:bold");
                Label chance2 = new Label("Chances: "+queryResult1.getString("odd2"));
                chance2.setLayoutX(240);
                chance2.setLayoutY(50);
                chance2.setStyle("-fx-text-fill:BLACK;-fx-font-weight:bold");

                Label date = new Label("Start time: "+queryResult1.getString("start"));
                date.setLayoutX(130);
                date.setLayoutY(100);

                date.setStyle("-fx-text-fill:RED;-fx-font-weight:bold");


                AnchorPane anchorpane = new AnchorPane();
                anchorpane.getChildren().add(imageView);
                anchorpane.getChildren().add(imageView2);
                anchorpane.getChildren().add(chance1);
                anchorpane.getChildren().add(chance2);
                anchorpane.getChildren().add(date);
                anchorpane.setBackground(new Background(new BackgroundFill(Color.rgb(32,74,76), new CornerRadii(1), new Insets(0.0, 0.0, 0.0, 0.0))));
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

    public void historyButtonOnAction (ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/baseproject/history_view_admin.fxml"));
            Parent root = (Parent) loader.load();
            Stage stage = (Stage) historyButton.getScene().getWindow();
            stage.close();
            HistoryViewAdminController historyViewAdminController = loader.getController();
            historyViewAdminController.updateAdmin();
            stage = new Stage();
            stage.setScene(new Scene(root,900,650));
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
    }

}
