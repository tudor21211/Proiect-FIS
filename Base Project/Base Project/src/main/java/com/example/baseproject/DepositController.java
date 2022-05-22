package com.example.baseproject;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.control.action.Action;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class DepositController  {

    @FXML
    private Button cancelDepositButton;
    @FXML
    private Button confirmDepositButton;
    @FXML
    public TextField valueField;
    @FXML
    private TextField cardField;
@FXML
private Label invalidCard;
    private Stage stage;
    private Scene scene;
    private double xOffset = 0;
    private double yOffset = 0;



    public void depositCancelButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) cancelDepositButton.getScene().getWindow();
        stage.close();
    }

    public String getValue(TextField t) {
        return t.getText();
    }

    public Double returnSum (Double a, Double b) {
        return a+b;
    }


    public void setBalanceField2(String username) {
        DBConnection connectNow = new DBConnection();
        Connection connection = null;
        Connection connectDB = connectNow.getConnection();
        PreparedStatement psInsert = null;
        DepositController depositController = new DepositController();
        String value = depositController.getValue(valueField);
        String getBalance = "SELECT balance from user_account where username='" + username + "'";
        ResultSet bal = null;
        double intValue = 0;
        try {
            Statement statement = connectDB.createStatement();
            bal = statement.executeQuery(getBalance);
            while (bal.next()) {
                String balance = bal.getString("balance");
                intValue = Double.parseDouble(value);
                intValue = returnSum(intValue,Double.parseDouble(String.valueOf(balance)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        value = Double.toString(intValue);
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/usersdb", "root", "root");
            psInsert = connection.prepareStatement("UPDATE user_account SET balance =? WHERE username=?");
            psInsert.setString(1, value);
            psInsert.setString(2, username);
            psInsert.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            e.getCause();
        }
    }

   public void confirmDepositButtonOnAction(ActionEvent event) throws IOException {
       DBConnection connectNow = new DBConnection();
       String verifyCard = "SELECT count(1) FROM cards WHERE cardDetail ='" + cardField.getText()+"'";
       Connection connectDB = connectNow.getConnection();
       try {
           Statement statement = connectDB.createStatement();
           ResultSet card = statement.executeQuery(verifyCard);
           while(card.next()) {
               if (card.getInt(1)==1) {
                   setBalanceField2(UserDetails.username);
                   Stage st = (Stage) confirmDepositButton.getScene().getWindow();
                   st.close();}
               else invalidCard.setText("Invalid card details");
           }
       } catch (SQLException e) {
           e.printStackTrace();
       }


   }



}
