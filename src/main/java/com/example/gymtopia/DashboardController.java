package com.example.gymtopia;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    private Label memberLbl;
    @FXML
    private Label coachLbl;
    @FXML
    private Label membershipLbl;
    @FXML
    private Label sessionLbl;
    @FXML
    private BarChart bar;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;

    Connection connection = null;
    String query = "";
    PreparedStatement preparedStatement = null ;
    ResultSet resultSet = null ;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        connection = databaseConnection.getConnection();
        memberLbl.setText(String.valueOf(memberNumber())+ " Member");
        coachLbl.setText(String.valueOf(coachNumber())+ " Coach");
        membershipLbl.setText(String.valueOf(membershipNumber())+ " Membership");
        barChart();
    }


    private int memberNumber(){
        String verifyLogin  = "select Count(MName) from members";
            try{
                Statement statement = connection.createStatement();
                ResultSet queryResult = statement.executeQuery(verifyLogin);
                int x = 0;
                while (queryResult.next()){
                    x = queryResult.getInt(1);}

                return x ;
            }
            catch (Exception e){
                e.printStackTrace();
            }
        return 0;
    }

    private int coachNumber(){
        String verifyLogin  = "select Count(TName) from trainers";
        try{
            Statement statement = connection.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);
            int x = 0;
            while (queryResult.next()){
                x = queryResult.getInt(1);}

            return x ;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    private int sessionNumber(){
        String verifyLogin  = "select Count(SName) from sessions";
        try{
            Statement statement = connection.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);
            int x = 0;
            while (queryResult.next()){
                x = queryResult.getInt(1);}

            return x ;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }
    private int membershipNumber(){
        String verifyLogin  = "select Count(MName) from members Where MExpiry < date(now())";
        try{
            Statement statement = connection.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);
            int x = 0;
            while (queryResult.next()){
                x = queryResult.getInt(1);}

            return x ;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    private void barChart(){
        try {
            XYChart.Series dataSeries1 = new XYChart.Series();

            query = "SELECT SName, SMembers FROM sessions";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){

                dataSeries1.getData().add(new XYChart.Data(resultSet.getString("SName"), resultSet.getInt("SMembers"))) ;
            }
            bar.getData().add(dataSeries1);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    @FXML
    private void switchtoMember(){
        try {
            HelloApplication.setScene((new FXMLLoader(getClass().getResource("Member.fxml"))).load());
            HelloApplication.UpdateStage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void switchtoDiet(){
        try {
            HelloApplication.setScene((new FXMLLoader(getClass().getResource("Diet.fxml"))).load());
            HelloApplication.UpdateStage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void switchtoSession(){
        try {
            HelloApplication.setScene((new FXMLLoader(getClass().getResource("Session.fxml"))).load());
            HelloApplication.UpdateStage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void switchtoCoach(){
        try {
            if(HelloApplication.isAdmin){
            HelloApplication.setScene((new FXMLLoader(getClass().getResource("Coach.fxml"))).load());
            HelloApplication.UpdateStage();
            }
            else {
                Alert al = new Alert(Alert.AlertType.ERROR);

                al.setContentText("Access Not Granted");
                al.showAndWait();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void switchtoDashboard(){
        try {
            HelloApplication.setScene((new FXMLLoader(getClass().getResource("Dashboard.fxml"))).load());
            HelloApplication.UpdateStage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void switchtoLogout(){
        try {
            HelloApplication.setScene((new FXMLLoader(getClass().getResource("Login.fxml"))).load());
            HelloApplication.UpdateStage();
            HelloApplication.isAdmin = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
