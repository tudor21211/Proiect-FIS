package com.example.baseproject;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.controlsfx.control.action.Action;

import java.io.IOException;
import java.sql.*;

public class DepositController {

    @FXML
    private Button cancelDepositButton;
    @FXML
    private Button confirmDepositButton;
    @FXML
    public TextField valueField;
    @FXML
    private TextField cardField;
    @FXML
    private Label testLabel;
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

    public void setBalanceField2(String username) {
        DBConnection connectNow = new DBConnection();
        Connection connection = null;
        Connection connectDB = connectNow.getConnection();
        PreparedStatement psInsert = null;
        DepositController depositController = new DepositController();
        String value = depositController.getValue(valueField);
        String getBalance = "SELECT balance from user_account where username='" + username + "'";
        ResultSet bal = null;
        int intValue = 0;
        try {
            Statement statement = connectDB.createStatement();
            bal = statement.executeQuery(getBalance);
            while (bal.next()) {
                String balance = bal.getString("balance");
                intValue = Integer.parseInt(value);
                intValue = intValue + Integer.parseInt(String.valueOf(balance));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        value = Integer.toString(intValue);
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
        //viewController.setBalanceField();

       FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/baseproject/main_view.fxml"));
       Parent root = (Parent) loader.load();
       ViewController viewController = loader.getController();
       setBalanceField2("t1");// aici am ramas, nu pot seta setBalanceField2() sa seteze cu userul conectat curent setBalanceField2(viewController.usernameField.getText())
      //dupa ce dai deposit teoretic se inchide scena cu usernameu si e parametru de username e nul
       Stage st = (Stage) confirmDepositButton.getScene().getWindow();
       st.hide();

   }



}
