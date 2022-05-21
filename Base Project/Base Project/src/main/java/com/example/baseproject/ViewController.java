package com.example.baseproject;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.PasswordAuthentication;
import java.sql.*;
import java.util.Random;
import java.util.ResourceBundle;
import java.net.URL;
import java.util.random.*;


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
    @FXML
    public Button resultsButton;
    @FXML
    public Button reloadButton;

    private Stage stage;
    private Scene scene;
    private double xOffset = 0;
    private double yOffset = 0;
    public Accordion accordion;


    private static final String IDLE_BUTTON_STYLE = "-fx-background-color:  #191C1C; -fx-text-fill: #FFFFFF; -fx-font-weight:bold; -fx-cursor:hand";
    private static final String HOVERED_BUTTON_STYLE = "-fx-background-color:#353438; -fx-shadow-highlight-color:#141f1f; -fx-text-fill: #FFFFFF;-fx-font-weight:bold; -fx-cursor:hand";
    private static final String IDLE_BUTTON_STYLE_Cancel = "-fx-background-color:   #FF0000; -fx-text-fill: #FFFFFF;-fx-font-weight:bold; -fx-cursor:hand";
    private static final String HOVERED_BUTTON_STYLE_Cancel = "-fx-background-color:#ff4000; -fx-shadow-highlight-color:#ff4000; -fx-text-fill: #FFFFFF;-fx-font-weight:bold; -fx-cursor:hand";
    private static final String LABEL = "-fx-font-weight:bold;-fx-text-fill:BLACK";
    private static final String TITLED_PANE = "-fx-background-color:GREEN;-fx-text-fill:BLUE;-fx-font-width:bold";
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


    public Button setButtonStyle (Button b, String s) {
        b.setStyle(s);
        return b;
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
    public void update()
    {
        DBConnection connectNow = new DBConnection();
        Connection connectDB = connectNow.getConnection();
        Connection connection = null;
        PreparedStatement psInsert = null;
        String getMatch = "SELECT * FROM matches WHERE start>now()";
        Accordion acc = new Accordion();
        //PreparedStatement psInsert = null;
        try{
            Statement statement1 = connectDB.createStatement();
            ResultSet queryResult1 = statement1.executeQuery(getMatch);
            while(queryResult1.next()) {
                TitledPane pane = new TitledPane();
                pane.setMinWidth(400);
                pane.setText(queryResult1.getString("team1") + " vs "+queryResult1.getString("team2"));
                pane.setStyle("-fx-text-fill:BLACK;");
                //Image image  = new Image(getClass().getResourceAsStream("FAZE.png"));
                Image image = new Image(new FileInputStream(queryResult1.getString("team2")+".jpg"));
                ImageView imageView = new ImageView(image);
                imageView.setFitHeight(100.0);
                imageView.setFitWidth(100.0);
                imageView.setX(400);
                imageView.setY(15);
                image = new Image(new FileInputStream(queryResult1.getString("team1")+".jpg"));
                ImageView imageView2 = new ImageView(image);
                imageView2.setFitHeight(100.0);
                imageView2.setFitWidth(100.0);
                imageView2.setX(20);
                imageView2.setY(15);
                Label chance1 = new Label("Chances: "+queryResult1.getString("odd1"));
                chance1.setLayoutX(140);
                chance1.setLayoutY(50);
                chance1.setStyle(LABEL);
                Label chance2 = new Label("Chances: "+queryResult1.getString("odd2"));
                chance2.setLayoutX(310);
                chance2.setLayoutY(50);
                chance2.setStyle(LABEL);
                Label date = new Label("Start time: "+queryResult1.getString("start"));
                date.setLayoutX(180);
                date.setLayoutY(100);
                date.setStyle("-fx-text-fill:RED;-fx-font-weight:bold");

                Button bet1 = new Button("PLACE BET");
                bet1.setLayoutX(30);
                bet1.setLayoutY(120);
                bet1.setTextFill(Color.WHITE);
                bet1.setStyle(IDLE_BUTTON_STYLE);

                TextField amount1 = new TextField();
                amount1.setPromptText("Amount...");
                amount1.setLayoutX(30);
                amount1.setLayoutY(150);
                amount1.setPrefWidth(80);

                TextField amount2 = new TextField();
                amount2.setPromptText("Amount...");
                amount2.setLayoutX(410);
                amount2.setLayoutY(150);
                amount2.setPrefWidth(80);

                Button bet2 = new Button("PLACE BET");
                bet2.setLayoutX(410);
                bet2.setLayoutY(120);
                bet2.setStyle(IDLE_BUTTON_STYLE);
                String id_matches = queryResult1.getString("idmatches");
                String team1 = queryResult1.getString("team1");
                String _chance1 = queryResult1.getString("odd1");
                bet1.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        PreparedStatement psInsert = null;
                        if (Double.parseDouble(UserDetails.balance)<Double.parseDouble(amount1.getText()))
                        {

                        }
                        else{
                            try {
                                Statement statement1 = connectDB.createStatement();
                                psInsert = connectDB.prepareStatement("INSERT INTO bets (user,match_id,team,amount,rate) values (?,?,?,?,?);");
                                psInsert.setString(1,UserDetails.username);
                                psInsert.setString(2,id_matches);
                                psInsert.setString(3,team1);
                                psInsert.setString(4, amount1.getText());
                                psInsert.setString(5, _chance1);
                                psInsert.execute();
                            }catch (SQLException e) {
                                e.printStackTrace();
                            }
                            UserDetails.balance = Double.toString(Double.parseDouble(UserDetails.balance) - Double.parseDouble(amount1.getText()));
                            try{
                                Statement statement = connectDB.createStatement();
                                String setBalance = "UPDATE user_account SET balance ='"+Double.parseDouble(UserDetails.balance)+"' WHERE username='"+usernameField.getText()+"'";
                                psInsert = connectDB.prepareStatement(setBalance);
                                psInsert.execute();

                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                        balanceField.setText(UserDetails.balance);

                    }
                });

                String team2 = queryResult1.getString("team2");
                String _chance2 = queryResult1.getString("odd2");
                bet2.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        PreparedStatement psInsert = null;
                        if (Double.parseDouble(UserDetails.balance)<Double.parseDouble(amount2.getText()))
                        {
                        }
                        else{
                            try {
                                Statement statement1 = connectDB.createStatement();
                                psInsert = connectDB.prepareStatement("INSERT INTO bets (user,match_id,team,amount,rate) values (?,?,?,?,?);");
                                psInsert.setString(1,UserDetails.username);
                                psInsert.setString(2,id_matches);
                                psInsert.setString(3,team2);
                                psInsert.setString(4, amount2.getText());
                                psInsert.setString(5, _chance2);
                                psInsert.execute();
                            }catch (SQLException e) {
                                e.printStackTrace();
                            }
                            UserDetails.balance = Double.toString(Double.parseDouble(UserDetails.balance) - Double.parseDouble(amount2.getText()));
                            try{
                                Statement statement = connectDB.createStatement();
                                String setBalance = "UPDATE user_account SET balance ='"+Double.parseDouble(UserDetails.balance)+"' WHERE username='"+usernameField.getText()+"'";
                                psInsert = connectDB.prepareStatement(setBalance);
                                psInsert.execute();

                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            balanceField.setText(UserDetails.balance);
                        }
                    }
                });

                AnchorPane anchorpane = new AnchorPane();
                anchorpane.getChildren().add(imageView);
                anchorpane.getChildren().add(imageView2);
                anchorpane.getChildren().add(chance1);
                anchorpane.getChildren().add(chance2);
                anchorpane.getChildren().add(date);
                anchorpane.getChildren().add(bet1);
                anchorpane.getChildren().add(bet2);
                anchorpane.getChildren().add(amount1);
                anchorpane.getChildren().add(amount2);
                anchorpane.setBackground(new Background(new BackgroundFill(Color.rgb(32,74,76), new CornerRadii(1), new Insets(0.0, 0.0, 0.0, 0.0))));
                pane.setContent(anchorpane);
                pane.setCollapsible(true);
                accordion.setExpandedPane(pane);
                accordion.getPanes().add(pane);

            }
            accordion.setVisible(true);

        }catch(SQLException e)
        {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        getMatch = "SELECT * FROM matches WHERE start<now()";
        try
        {
            Statement statement1 = connectDB.createStatement();
            ResultSet queryResult1 = statement1.executeQuery(getMatch);
            while (queryResult1.next())
            {
                Statement statement2 = connectDB.createStatement();
                String rez = queryResult1.getString("result");
                if (rez==null)
                {
                    int scor1=0;
                    int scor2=0;
                    Random rand = new Random();
                    Random rand2 = new Random();
                    while ((scor1!=16) && (scor2!=16))
                    {
                        System.out.println(scor1 + "-" + scor2);
                        scor1 = rand.nextInt(17);
                        scor2 = rand2.nextInt(17);
                    }
                    String str = Double.toString(scor1)+"-"+Double.toString(scor2);
                    System.out.println(str);
                    try {
                        psInsert = connectDB.prepareStatement("UPDATE matches SET result ='"+str+"' WHERE idmatches='"+queryResult1.getString("idmatches")+"'");
                        psInsert.execute();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

                
                ResultSet queryResult2 = statement2.executeQuery("SELECT * FROM bets WHERE match_id="+queryResult1.getString("idmatches"));
                while(queryResult2.next())
                {
                    if(queryResult2.getString("user").equals(UserDetails.username))
                    {
                        System.out.println(queryResult1.getString("result"));
                        String score = queryResult1.getString("result");
                        if (score==null) System.out.println(score); else {
                        assert score != null;
                        int index = score.indexOf("-");
                        double score_team1 = Double.parseDouble(score.substring(0,index));
                        String winner_team;
                        if (score_team1==16) {winner_team=queryResult1.getString("team1");}
                        else {winner_team=queryResult1.getString("team2");}
                        if(winner_team.equals(queryResult2.getString("team")))
                        {
                            connection = DriverManager.getConnection("jdbc:mysql://localhost/usersdb", "root", "root");
                            psInsert = connection.prepareStatement("UPDATE user_account SET balance =? WHERE username=?");
                            String s = Double.toString(Double.valueOf(UserDetails.balance) + Double.valueOf(queryResult2.getString("rate")) * Double.valueOf(queryResult2.getString("amount")));
                            psInsert.setString(1,s);
                            psInsert.setString(2, UserDetails.username);
                            psInsert.execute();
                            UserDetails.balance = s;
                            balanceField.setText(s);
                        }
                        connection = DriverManager.getConnection("jdbc:mysql://localhost/usersdb", "root", "root");
                        //psInsert = connection.prepareStatement("SET SQL_SAFE_UPDATES = 0; DELETE FROM bets where idbets="+queryResult2.getString("idbets") +";");
                        Statement sInsert;
                        sInsert = connection.createStatement();
                        sInsert.addBatch("SET SQL_SAFE_UPDATES = 0");
                        sInsert.addBatch("DELETE FROM bets where idbets="+queryResult2.getString("idbets"));
                        sInsert.executeBatch();

                    }
                }
            }}
        } catch (SQLException e) {
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

    public void historyButtonOnAction (ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/baseproject/history_view.fxml"));
            Parent root = (Parent) loader.load();
            Stage stage = (Stage) historyButton.getScene().getWindow();
            stage.close();
            HistoryViewController historyViewController = loader.getController();
            historyViewController.update2();
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

    public void resultsButtonOnAction(ActionEvent event){
        DBConnection connectNow = new DBConnection();
        Connection connectDB = connectNow.getConnection();
        Connection connection = null;
        PreparedStatement psInsert = null;
        String getMatch = "SELECT * FROM matches WHERE start<now()";
        try {
            Statement statement1 = connectDB.createStatement();
            ResultSet queryResult = statement1.executeQuery(getMatch);
            while (queryResult.next()) {
                try {
                    System.out.println(queryResult.getString("idmatches"));
                    psInsert = connectDB.prepareStatement("UPDATE matches SET start ='2001-01-01' WHERE idmatches=1+"+queryResult.getString("idmatches"));
                    psInsert.execute();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void reloadButtonOnAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/baseproject/main_view.fxml"));
            Parent root = (Parent) loader.load();
            Stage stage = (Stage) reloadButton.getScene().getWindow();
            ViewController viewController = loader.getController();
            viewController.setUsernameField(UserDetails.username);
            viewController.setBalanceField();
            stage.close();
            viewController.update();
            stage = new Stage();
            stage.setScene(new Scene(root, 1250, 850));
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
