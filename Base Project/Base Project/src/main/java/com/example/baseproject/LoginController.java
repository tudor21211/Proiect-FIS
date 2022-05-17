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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.stage.StageStyle;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.io.File;
import java.io.IOException;
import java.net.PasswordAuthentication;
import java.sql.*;
import java.util.ResourceBundle;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class LoginController implements Initializable {
    public Button backButton;
    public Button admLog;
    @FXML
    private TextField codeField;
    @FXML
    private Button loginButton;
 @FXML
    private Label invalidLogin;
 @FXML
 private ImageView brandingImageView;
 @FXML
 private ImageView lockImageView;
 @FXML
 private Button cancelButton,logAdmButton;
 @FXML
 private Button registerButton;
 @FXML
 private TextField userField;
 @FXML
 private PasswordField passField;
@FXML
private Label newRegistration;
private static Stage stage;
private Scene scene;
    private double xOffset = 0;
    private double yOffset = 0;

    private static final String IDLE_BUTTON_STYLE = "-fx-background-color:  #323D42; -fx-text-fill: #FFFFFF;";
    private static final String IDLE_BUTTON_STYLE_Cancel = "-fx-background-color:   #FF0000; -fx-text-fill: #FFFFFF;";
    private static final String HOVERED_BUTTON_STYLE_Cancel = "-fx-background-color:#ff4000; -fx-shadow-highlight-color:#ff4000; -fx-text-fill: #FFFFFF;";
    private static final String HOVERED_BUTTON_STYLE = "-fx-background-color:#141f1f; -fx-shadow-highlight-color:#141f1f; -fx-text-fill: #FFFFFF;";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loginButton.setStyle(IDLE_BUTTON_STYLE);
        loginButton.setOnMouseEntered(e -> loginButton.setStyle(HOVERED_BUTTON_STYLE));
        loginButton.setOnMouseExited(e -> loginButton.setStyle(IDLE_BUTTON_STYLE));
        registerButton.setStyle(IDLE_BUTTON_STYLE);
        registerButton.setOnMouseEntered(e -> registerButton.setStyle(HOVERED_BUTTON_STYLE));
        registerButton.setOnMouseExited(e -> registerButton.setStyle(IDLE_BUTTON_STYLE));
        logAdmButton.setStyle(IDLE_BUTTON_STYLE);
        logAdmButton.setOnMouseEntered(e -> logAdmButton.setStyle(HOVERED_BUTTON_STYLE));
        logAdmButton.setOnMouseExited(e -> logAdmButton.setStyle(IDLE_BUTTON_STYLE));
        cancelButton.setStyle(IDLE_BUTTON_STYLE_Cancel);
        cancelButton.setOnMouseEntered(e -> cancelButton.setStyle(HOVERED_BUTTON_STYLE_Cancel));
        cancelButton.setOnMouseExited(e -> cancelButton.setStyle(IDLE_BUTTON_STYLE_Cancel));




        registerButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                if (!userField.getText().trim().isEmpty() && !passField.getText().trim().isEmpty()) {
                    byte[] md5InBytes = digest(passField.getText().getBytes(UTF_8));
                    DBUtils.signUpUser(userField.getText(),String.format(OUTPUT_FORMAT, "", bytesToHex(md5InBytes)),0);
                    //newRegistration.setText("User "+userField.getText()+" registered succesfully");
                    invalidLogin.setText("");

                }else
                {
                    invalidLogin.setText("Fill all info to register");
                    System.out.println("Fill in all info");
                }
            }
        });
    }

    private static final Charset UTF_8 = StandardCharsets.UTF_8;
    private static final String OUTPUT_FORMAT = "%-20s:%s";

    private static byte[] digest(byte[] input) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
        byte[] result = md.digest(input);
        return result;
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }


    public void loginButtonOnAction() {
        if (!userField.getText().isBlank() && !passField.getText().isBlank()) {
            invalidLogin.setText("Check login...");
            newRegistration.setText("");
            validateLogin();
        }else
        {
            invalidLogin.setText("Enter user and password please...");
            newRegistration.setText("");
        }
    }


    public void cancelButtonOnAction () {

     Stage stage = (Stage) cancelButton.getScene().getWindow();
     stage.close();

    }

    public void logAdmButtonOnAction()
    {
        codeField.setVisible(true);
        backButton.setVisible(true);
        admLog.setVisible(true);
    }

    public void backButtonOnAction()
    {
        codeField.setVisible(false);
        backButton.setVisible(false);
        admLog.setVisible(false);
    }

    public void admLogOnAction()
    {
        if (!codeField.getText().isBlank())
        {
            validateAdminLogin();
        }
    }




    public void validateLogin(){
            DBConnection connectNow = new DBConnection();
            Connection connectDB = connectNow.getConnection();
            byte[] md5InBytes = digest(passField.getText().getBytes(UTF_8));
            String parola = String.format(OUTPUT_FORMAT, "", bytesToHex(md5InBytes));
            String verifyLogin = "SELECT count(1) FROM user_account WHERE username ='" + userField.getText() + "' AND password ='" + parola + "'";
            String getBalance = "SELECT balance from user_account where username='"+userField.getText()+"'";

        try {

                Statement statement = connectDB.createStatement();
                ResultSet queryResult = statement.executeQuery(verifyLogin);
                while (queryResult.next()) {
                    if (queryResult.getInt(1)==1){
                       // LoginApplication.changeScene2("main_view.fxml");
                        try {
                            Statement statement1 = connectDB.createStatement();
                            ResultSet queryResult1 = statement1.executeQuery(getBalance);
                            while(queryResult1.next()) {
                                UserDetails.balance = queryResult1.getString("balance");
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        UserDetails.username = userField.getText();
                        UserDetails.password=passField.getText();
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/baseproject/main_view.fxml"));
                            Parent root = (Parent) loader.load();
                            ViewController viewController = loader.getController();//de linia asta
                            viewController.setUsernameField(userField.getText());//si de linia asta e nevoie sa poti seta username-ul, teoretic fereastra noua nu inchide pe cea veche din cauza loader-ului dar fara el nu poti seta username-ul in scena noua
                            viewController.setBalanceField();
                            Stage stage = (Stage) loginButton.getScene().getWindow();
                            viewController.update();
                            stage.close();
                            stage = new Stage();
                            stage.setScene(new Scene(root,1250,850));
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
                        invalidLogin.setText("Welcome "+userField.getText());
                        newRegistration.setText("");
                    }else {
                        invalidLogin.setText("Invalid login credentials");
                        newRegistration.setText("");
                    }
                }

            }catch (Exception e){
                e.printStackTrace();
                e.getCause();
            }

    }


    public void validateAdminLogin()
    {
        DBConnection connectNow = new DBConnection();
        Connection connectDB = connectNow.getConnection();
        byte[] md5InBytes = digest(codeField.getText().getBytes(UTF_8));
        String cod = String.format(OUTPUT_FORMAT, "", bytesToHex(md5InBytes));
        cod = cod.substring(cod.lastIndexOf(":")+1);
        System.out.println(cod);
        String verifyCode = "SELECT count(1) FROM admin_codes WHERE code ='"+cod+"'";

        try
        {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyCode);

            while (queryResult.next())
            {
                if(queryResult.getInt(1)==1)
                {

                    invalidLogin.setText("Welcome "+codeField.getText());
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/baseproject/admin_view.fxml"));
                        Parent root = (Parent) loader.load();
                        Stage stage = (Stage) loginButton.getScene().getWindow();
                        AdminViewController a = loader.getController();
                        a.update();
                        stage.close();
                        stage = new Stage();
                        stage.setScene(new Scene(root,1250,850));
                        stage.initStyle(StageStyle.UNDECORATED);
                        stage.show();
                    }catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    invalidLogin.setText("Invalid code");
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            e.getCause();
        }

    }




}