package com.example.baseproject;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.loadui.testfx.GuiTest;
import org.w3c.dom.Text;
import org.testfx.api.FxRobot;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Before;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.Assert.*;

class DepositControllerTest {


    @Test
    void returnSum() {
        DepositController s = new DepositController();
        assertEquals(4,s.returnSum(2.0,2.0));
    }

    @Test
    public void  setBalanceField2(FxRobot robot) {
        Label label = (Label) GuiTest.find("#label");

        robot.clickOn("#valueField");
        robot.write("C'est un test!");
        robot.clickOn("#applyButton");
        MatcherAssert.assertThat(label.getText(), is("C'est un test!"));
    }
}