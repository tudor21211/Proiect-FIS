package com.example.baseproject;

import java.sql.Connection;
import java.sql.DriverManager;


public class DBConnection {
    public Connection dataBaseLink;

    public Connection getConnection(){
        String dataBaseName = "usersdb";
        String dataBaseUser = "root";
        String dataBasePassword = "root";
        String url = "jdbc:mysql://localhost/" + dataBaseName;
        try {
                Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                dataBaseLink = DriverManager.getConnection(url,dataBaseUser,dataBasePassword);

        }catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    return dataBaseLink;
    }
}
