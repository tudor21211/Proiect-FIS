package com.example.baseproject;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WithdrawControllerTest {

    @Test
    void returnDifference() {
        WithdrawController w = new WithdrawController();
        assertEquals(3,w.returnDifference(2.0,1.0));
    }
}