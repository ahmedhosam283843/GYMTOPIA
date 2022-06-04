package com.example.gymtopia;

import java.sql.Connection;
import java.sql.DriverManager;


public class DatabaseConnection {
    public Connection databaseLink;

    public Connection getConnection() {
        String databaseName = "gym_database";
        String databaseUser = "root";
        String databasePassword = "123ahmed";
        String url =  "jdbc:mysql://localhost/" + databaseName;

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink = DriverManager.getConnection(url, databaseUser, databasePassword);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return databaseLink;


    }
}
