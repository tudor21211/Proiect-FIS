package com.example.baseproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.control.action.Action;

import java.io.IOException;
import java.sql.*;

public class DBUtils {
    public static void signUpUser(String username, String password, int  balance) {
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckUsername = null;
        ResultSet resultSet = null;

        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost/usersdb","root","root");
            psCheckUsername = connection.prepareStatement("SELECT * FROM user_account WHERE username = ?");
            psCheckUsername.setString(1,username);

            resultSet = psCheckUsername.executeQuery();

            if (resultSet.isBeforeFirst()){
                System.out.println("User already exists");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Username already taken");
                alert.show();
            }
            else {
                psInsert = connection.prepareStatement("INSERT INTO user_account (username, password, balance) VALUES (?,?,?);");
                psInsert.setString(1,username);
                psInsert.setString(2,password);
                psInsert.setInt(3,0);
                psInsert.execute(); // de ce nu executeQuery()?
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("User "+username+" successfully registered");
                alert.setTitle("Succes");
                alert.setHeaderText("");
                alert.show();
            }
        }catch (SQLException e) {
            e.printStackTrace();
            e.getCause();
        }
        finally {
            if (resultSet != null){
                try {
                    resultSet.close();
                }catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psCheckUsername != null) {
                try {
                    psCheckUsername.close();
                }catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psInsert != null) {
                try {
                    psInsert.close();
                }catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                }catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    }
