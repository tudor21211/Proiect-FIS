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
import javafx.stage.StageStyle;

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
    @FXML
    public Button withdrawButton;
    @FXML
    public Button depositButton;
    @FXML
    public Button historyButton;


    private Stage stage;
    private Scene scene;
    private double xOffset = 0;
    private double yOffset = 0;
    private Accordion accordion;


    private static final String IDLE_BUTTON_STYLE = "-fx-background-color:  #191C1C; -fx-text-fill: #FFFFFF; -fx-font-weight:bold; -fx-cursor:hand";
    private static final String HOVERED_BUTTON_STYLE = "-fx-background-color:#353438; -fx-shadow-highlight-color:#141f1f; -fx-text-fill: #FFFFFF;-fx-font-weight:bold; -fx-cursor:hand";
    private static final String IDLE_BUTTON_STYLE_Cancel = "-fx-background-color:   #FF0000; -fx-text-fill: #FFFFFF;-fx-font-weight:bold; -fx-cursor:hand";
    private static final String HOVERED_BUTTON_STYLE_Cancel = "-fx-background-color:#ff4000; -fx-shadow-highlight-color:#ff4000; -fx-text-fill: #FFFFFF;-fx-font-weight:bold; -fx-cursor:hand";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        withdrawButton.setStyle(IDLE_BUTTON_STYLE);
        withdrawButton.setOnMouseEntered(e -> withdrawButton.setStyle(HOVERED_BUTTON_STYLE));
        withdrawButton.setOnMouseExited(e -> withdrawButton.setStyle(IDLE_BUTTON_STYLE));
        depositButton.setStyle(IDLE_BUTTON_STYLE);
        depositButton.setOnMouseEntered(e -> depositButton.setStyle(HOVERED_BUTTON_STYLE));
        depositButton.setOnMouseExited(e -> depositButton.setStyle(IDLE_BUTTON_STYLE));
        historyButton.setStyle(IDLE_BUTTON_STYLE);
        historyButton.setOnMouseEntered(e -> historyButton.setStyle(HOVERED_BUTTON_STYLE));
        historyButton.setOnMouseExited(e -> historyButton.setStyle(IDLE_BUTTON_STYLE));
        logoutButton.setStyle(IDLE_BUTTON_STYLE_Cancel);
        logoutButton.setOnMouseEntered(e -> logoutButton.setStyle(HOVERED_BUTTON_STYLE_Cancel));
        logoutButton.setOnMouseExited(e -> logoutButton.setStyle(IDLE_BUTTON_STYLE_Cancel));
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
            while (bal.next()) {
                String balance = bal.getString("balance");
                balanceField.setText(balance);
                System.out.println("Balance:"+balance);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setBalanceField2(){
        DBConnection connectNow = new DBConnection();
        Connection connectDB = connectNow.getConnection();
        //System.out.println(usernameField);
        DepositController depositController = new DepositController();
        String value = depositController.getValue(depositController.valueField);
        String setBalance = "UPDATE user_account SET balance ='"+value+"' WHERE username='"+usernameField.getText()+"'";
    }


    public void depositButtonOnAction(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/baseproject/deposit_view.fxml"));
            Parent root = (Parent) loader.load();
            stage = new Stage();
            stage.setScene(new Scene(root,400,200));
            stage.initStyle(StageStyle.UNDECORATED);
            Stage finalStage = stage;
            root.setOnMousePressed(new EventHandler <MouseEvent> () {
                @Override
                public void handle(MouseEvent event) {
                    xOffset = finalStage.getX() - event.getScreenX();
                    yOffset = finalStage.getY() - event.getScreenY();
                }
            });

            Stage finalStage1 = stage;
            root.setOnMouseDragged(new EventHandler < MouseEvent > () {
                @Override
                public void handle(MouseEvent event) {
                    finalStage1.setX(event.getScreenX() + xOffset);
                    finalStage1.setY(event.getScreenY() + yOffset);
                }
            });
            stage.show();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void withdrawButtonOnAction(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/baseproject/withdraw_view.fxml"));
            Parent root = (Parent) loader.load();
            stage = new Stage();
            stage.setScene(new Scene(root,400,200));
            stage.initStyle(StageStyle.UNDECORATED);
            Stage finalStage = stage;
            root.setOnMousePressed(new EventHandler <MouseEvent> () {
                @Override
                public void handle(MouseEvent event) {
                    xOffset = finalStage.getX() - event.getScreenX();
                    yOffset = finalStage.getY() - event.getScreenY();
                }
            });

            Stage finalStage1 = stage;
            root.setOnMouseDragged(new EventHandler < MouseEvent > () {
                @Override
                public void handle(MouseEvent event) {
                    finalStage1.setX(event.getScreenX() + xOffset);
                    finalStage1.setY(event.getScreenY() + yOffset);
                }
            });
            stage.show();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}

