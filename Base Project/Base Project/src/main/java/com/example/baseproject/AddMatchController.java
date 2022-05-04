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

public class AddMatchController extends ViewController{
    @FXML
    private Button CreateMatchButton;

    private Stage stage;
    private Scene scene;

    @FXML
    private ComboBox comboBoxT1;

    @FXML
    private ComboBox comboBoxT2;

    @FXML
    private TextField startTime;

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

    public void createMatchButtonOnAction(ActionEvent event)
    {
        DBConnection connectNow = new DBConnection();
        Connection connectDB = connectNow.getConnection();
        String team1 =comboBoxT1.getValue().toString();
        String team2 =comboBoxT2.getValue().toString();
        PreparedStatement psInsert = null;

        try {
            Statement statement = connectDB.createStatement();
            psInsert = connectDB.prepareStatement("INSERT INTO matches (team1,team2,start) values (?,?,?);");
            psInsert.setString(1,team1);
            psInsert.setString(2,team2);
            psInsert.setString(3,startTime.getText());
            psInsert.execute(); // de ce nu executeQuery()?
        }catch (SQLException e) {
            e.printStackTrace();
        }
        try {

            Parent root = FXMLLoader.load(getClass().getResource("/com/example/baseproject/admin_view.fxml"));
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
