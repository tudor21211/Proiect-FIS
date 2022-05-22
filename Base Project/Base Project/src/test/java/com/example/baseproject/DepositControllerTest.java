package com.example.baseproject;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DepositControllerTest {

    @Test
    void returnSum() {
        DepositController depositController = new DepositController();
        assertEquals(4,depositController.returnSum(2.0,2.0));
    }
}