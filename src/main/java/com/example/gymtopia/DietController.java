package com.example.gymtopia;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DietController implements Initializable {

    @FXML
    private TextField weighttf;
    @FXML
    private TextField heighttf;
    @FXML
    private TextField agetf;
    @FXML
    private ChoiceBox genderCombo;
    @FXML
    private ChoiceBox activityCombo;
    @FXML
    private Label calorieLbl;
    @FXML
    private Label proteinLbl;
    @FXML
    private Label fatsLbl;
    @FXML
    private Label carbsLbl;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String[] genders = {"Male", "Female"};
        genderCombo.setItems(FXCollections.observableArrayList(genders));
        String[] activityLvl = {"Sedentary", "Lightly active", "Moderately active", "Very active", "Extra active"};
        activityCombo.setItems(FXCollections.observableArrayList(activityLvl));
    }

    @FXML
    private void btnOnAction(){

        String sw = weighttf.getText();
        String sh = heighttf.getText();
        String sa = agetf.getText();
        String gender = String.valueOf(genderCombo.getValue());
        String activity = String.valueOf(activityCombo.getValue());

        if(sw.isEmpty()||sh.isEmpty() || sa.isEmpty() || gender.isEmpty() || activity.isEmpty() || gender == null ||activity ==null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Fill All DATA");
            alert.showAndWait();
        }
        else {
            Double weight  = Double.parseDouble(weighttf.getText());
            Double height  = Double.parseDouble(heighttf.getText());
            Double age  = Double.parseDouble(agetf.getText());

            calorieLbl.setText(String.valueOf(new Diet(age, weight, height, activity, gender).calculateCalories()) + "KCal");
            carbsLbl.setText(String.valueOf(new Diet(age, weight, height, activity, gender).calculateCarbs()) + "gm");
            proteinLbl.setText(String.valueOf(new Diet(age, weight, height, activity, gender).calculateProtein()) + "gm");
            fatsLbl.setText(String.valueOf(new Diet(age, weight, height, activity, gender).calculateFats()) + "gm");
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
            HelloApplication.isAdmin  = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
