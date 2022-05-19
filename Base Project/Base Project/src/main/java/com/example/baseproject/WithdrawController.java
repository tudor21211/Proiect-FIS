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

public class WithdrawController  {

    @FXML
    private Button cancelWithdrawButton;
    @FXML
    private Button confirmWithdrawButton;
    @FXML
    public TextField valueField;
    @FXML
    private Label invalid;
    private Stage stage;
    private Scene scene;
    private double xOffset = 0;
    private double yOffset = 0;
    private static int ok=1;


    public void withdrawCancelButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) cancelWithdrawButton.getScene().getWindow();
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
        WithdrawController withdrawController = new WithdrawController();
        String value = withdrawController.getValue(valueField);
        String getBalance = "SELECT balance from user_account where username='" + username + "'";
        ResultSet bal = null;
        double intValue = 0;
        try {
            Statement statement = connectDB.createStatement();
            bal = statement.executeQuery(getBalance);
            while (bal.next()) {
                String balance = bal.getString("balance");
                intValue = Double.parseDouble(value);
                intValue = Double.parseDouble(String.valueOf(balance)) - intValue;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (intValue < 0) {invalid.setText("Insufficient Funds!!!");ok=0;
            }
        else {
            value = Double.toString(intValue);
            try {
                connection = DriverManager.getConnection("jdbc:mysql://localhost/usersdb", "root", "root");
                psInsert = connection.prepareStatement("UPDATE user_account SET balance =? WHERE username=?");
                psInsert.setString(1, value);
                psInsert.setString(2, username);
                psInsert.execute();
                Stage st = (Stage) confirmWithdrawButton.getScene().getWindow();
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
                e.getCause();
            }
        }
    }
    public void confirmWithdrawButtonOnAction(ActionEvent event) throws IOException {
                    setBalanceField2(UserDetails.username);
    }


    }




