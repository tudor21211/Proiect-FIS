package com.example.baseproject;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import org.controlsfx.control.spreadsheet.Grid;

public class Test {

    @FXML
    Accordion accordion;
    @FXML
    Button test;
    TitledPane titledPane = new TitledPane();


    public void addTitledPanes(TitledPane titledPane) {
        accordion.getPanes().add(titledPane);
    }

    public void testOnAction (){
        TitledPane titledPane = new TitledPane();
        Button b1 = new Button();
        titledPane.setText("Grid");
        titledPane.setAnimated(true);
        titledPane.setContent(b1);
        titledPane.setCollapsible(true);
        addTitledPanes(titledPane);
    }

    

}
