package com.example.gymtopia;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SessionController implements Utility , Initializable {
    @FXML
    private TableView<Classes> table;
    @FXML
    private TextField nametf;
    @FXML
    private TableColumn<Classes, String> nameCol;
    @FXML
    private TableColumn<Classes, String> dayCol;
    @FXML
    private TableColumn<Classes, String> timeCol;
    @FXML
    private TableColumn<Classes, String> trainerCol;
    @FXML
    private TableColumn<Classes, String> membersCol;
    @FXML
    private ChoiceBox coachCombo;
    @FXML
    private ChoiceBox timeCombo;
    @FXML
    private ChoiceBox dayCombo;

    ObservableList<Classes> ClassList = FXCollections.observableArrayList();
    Connection connection = null;
    String query = "";
    Members session = null;
    PreparedStatement preparedStatement = null ;
    ResultSet resultSet = null ;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadData();
    }

    @FXML
    private void refreshTable() {
        try {
            ClassList.clear();

            query = "SELECT * FROM sessions";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                ClassList.add(new Classes(
                        resultSet.getString("SName"),
                        resultSet.getString("SDay"),
                        resultSet.getString("SSlot"),
                        resultSet.getString("STrainer"),
                        memberNumber(resultSet.getString("SName"))));
                table.setItems(ClassList);
            }



        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void loadData() {
        DatabaseConnection databaseConnection =new DatabaseConnection();
        connection = databaseConnection.getConnection();
        coachCombo(connection);
        String[] days = {"Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        dayCombo.setItems(FXCollections.observableArrayList(days));
        String[] timeslots = {"12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00"};
        timeCombo.setItems(FXCollections.observableArrayList(timeslots));

        refreshTable();


        nameCol.setCellValueFactory(new PropertyValueFactory<Classes, String>("Name"));
        dayCol.setCellValueFactory(new PropertyValueFactory<Classes, String>("weekday"));
        timeCol.setCellValueFactory(new PropertyValueFactory<Classes, String>("slot"));
        trainerCol.setCellValueFactory(new PropertyValueFactory<Classes, String>("coachName"));
        membersCol.setCellValueFactory(new PropertyValueFactory<Classes, String>("memberNumber"));

    }
    private void coachCombo(Connection connection)  {
        try {
            query = "SELECT TName FROM `trainers`";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            ArrayList<String> coaches = new ArrayList<>();
            while (resultSet.next()) {
                coaches.add(resultSet.getString("TName"));
            }
            coachCombo.setItems(FXCollections.observableArrayList(coaches));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private int memberNumber(String name){
        String verifyLogin  = "select Count(MName) from members Where MClass='"+ name+ "'";
        if(name!=null && !name.isEmpty())

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


    @Override
    @FXML
    public void ADD() {
        String name =  nametf.getText();
        String coach  = String.valueOf(coachCombo.getValue());
        String day  = String.valueOf(dayCombo.getValue());
        String time  = String.valueOf(timeCombo.getValue());
        int members = memberNumber(name);
        if(name.isEmpty() || coach.isEmpty() || day.isEmpty() || time.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Fill All DATA");
            alert.showAndWait();
        }
        else {
            try {
                query = "Insert into sessions (SName, SDay, STrainer, SSlot, SMembers) values(?,?,?,?,?)";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, day);
                preparedStatement.setString(3, coach);
                preparedStatement.setString(4, time);
                preparedStatement.setInt(5, members);
                preparedStatement.execute();
                clean();
                refreshTable();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText(null);
                alert.setContentText("Session Added!!");
                alert.showAndWait();

            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    private void clean(){
        nametf.setText(null);
        coachCombo.setValue(null);
        timeCombo.setValue(null);
        dayCombo.setValue(null);
    }

    @Override
    @FXML
    public void REMOVE() {
        String name = nametf.getText();
        Classes classes  = table.getSelectionModel().getSelectedItem();
        if(classes == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Select A Session!!");
            alert.showAndWait();
        }
        else{
            try {
                query = "Delete from sessions WHERE SName = '" + name + "'";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.execute();
                clean();
                refreshTable();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText(null);
                alert.setContentText("Session Deleted!!");
                alert.showAndWait();

            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    @FXML
    public void UPDATE() {
        String name =  nametf.getText();
        String coach  = String.valueOf(coachCombo.getValue());
        String day  = String.valueOf(dayCombo.getValue());
        String time  = String.valueOf(timeCombo.getValue());
        int members = memberNumber(name);
        Classes session  = table.getSelectionModel().getSelectedItem();
        if(name.isEmpty() || coach.isEmpty() || day.isEmpty() || time.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Fill All DATA");
            alert.showAndWait();
        }
        else if(session == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Select A Member!!");
            alert.showAndWait();
        }
        else {
            try {
                query = "Update sessions SET SName=?, SDay=?, STrainer=?, SSlot=?, SMembers=? WHERE SName = '" + name + "'";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, day);
                preparedStatement.setString(3, coach);
                preparedStatement.setString(4, time);
                preparedStatement.setInt(5, members);
                preparedStatement.execute();
                clean();
                refreshTable();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText(null);
                alert.setContentText("Session Updated!!");
                alert.showAndWait();

            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    @FXML
    public void showSelectedRow(){
        Classes session  = table.getSelectionModel().getSelectedItem();
        if(session!=null){
            nametf.setText(session.getName());
            coachCombo.setValue(session.getCoachName());
            dayCombo.setValue(session.getWeekday());
            timeCombo.setValue(session.getSlot());
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




