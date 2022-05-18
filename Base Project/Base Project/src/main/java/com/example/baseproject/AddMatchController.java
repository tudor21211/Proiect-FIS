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
import javafx.scene.control.Button;
import javafx.stage.StageStyle;

import static java.lang.Float.parseFloat;

public class AddMatchController extends ViewController{
    @FXML
    private Button CreateMatchButton;
    @FXML
    private Button cancelButton;
    private Stage stage;
    private Scene scene;

    @FXML
    private ComboBox comboBoxT1;

    @FXML
    private ComboBox comboBoxT2;

    @FXML
    private Label invalidLabel;

    @FXML
    private TextField startTime;

    @FXML
    private TextField odds1;
    @FXML
    private TextField odds2;

    private double xOffset = 0;
    private double yOffset = 0;
    private Accordion accordion;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {

        DBConnection connectNow = new DBConnection();
        Connection connectDB = connectNow.getConnection();

        String getTeamname = "Select (team_name) FROM teams";

        System.out.println("asd");
        try {
            Statement statement = connectDB.createStatement();
            ResultSet teams = statement.executeQuery(getTeamname);
            //balanceField.setText(bal.getString("balance"));
            while (teams.next()) {
                String team = teams.getString("team_name");
                System.out.println(team);
                comboBoxT1.getItems().add(
                        team
                );
                comboBoxT2.getItems().add(
                        team
                );
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createMatchButtonOnAction(ActionEvent event) {
        DBConnection connectNow = new DBConnection();
        Connection connectDB = connectNow.getConnection();
        if (comboBoxT1.getValue() == null || comboBoxT2.getValue() == null)
            invalidLabel.setText("Please insert all details");
        else {
            String team1 = comboBoxT1.getValue().toString();
            String team2 = comboBoxT2.getValue().toString();
            PreparedStatement psInsert = null;

            try {
                Statement statement = connectDB.createStatement();
                psInsert = connectDB.prepareStatement("INSERT INTO matches (team1,team2,start,odd1,odd2) values (?,?,?,?,?);");
                psInsert.setString(1, team1);
                psInsert.setString(2, team2);
                psInsert.setString(3, startTime.getText());
                psInsert.setString(4, String.valueOf(Float.parseFloat(odds1.getText())));
                psInsert.setString(5, String.valueOf(Float.parseFloat(odds2.getText())));
                psInsert.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {

//            Parent root = FXMLLoader.load(getClass().getResource("/com/example/baseproject/admin_view.fxml"));
//            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//            scene = new Scene (root);
//            stage.setScene(scene);
//            stage.centerOnScreen();
//            AdminViewController a = new AdminViewController();
//            a.update();
//            stage.show();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/baseproject/admin_view.fxml"));
                Parent root = (Parent) loader.load();
                AdminViewController a = loader.getController();
                a.update();
                Stage stage = (Stage) startTime.getScene().getWindow();
                stage.close();
                stage = new Stage();
                stage.setScene(new Scene(root));
                Stage finalStage = stage;
                stage.initStyle(StageStyle.UNDECORATED);
                root.setOnMousePressed(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        xOffset = finalStage.getX() - event.getScreenX();
                        yOffset = finalStage.getY() - event.getScreenY();
                    }
                });

                Stage finalStage1 = stage;
                root.setOnMouseDragged(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        finalStage1.setX(event.getScreenX() + xOffset);
                        finalStage1.setY(event.getScreenY() + yOffset);
                    }
                });
                stage.show();


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void cancelButtonOnAction(ActionEvent event) {


            // LoginApplication.changeScene1("login.fxml");

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
}
