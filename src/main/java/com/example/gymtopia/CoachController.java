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
import java.util.ResourceBundle;

public class CoachController implements Initializable, Utility {

    @FXML
    private TableView<Trainer> table;
    @FXML
    private TextField nametf;
    @FXML
    private TextField phonetf;
    @FXML
    private TextField passwordtf;
    @FXML
    private TextField usernametf;
    @FXML
    private TableColumn<Trainer, String> nameCol;
    @FXML
    private TableColumn<Trainer, String> usernameCol;
    @FXML
    private TableColumn<Trainer, String> passwordCol;
    @FXML
    private TableColumn<Trainer, String> phoneCol;
    @FXML
    private TableColumn<Trainer, String> classesCol;
    @FXML
    private TableColumn<Trainer, String> salaryCol;

    ObservableList<Trainer> TrainerList = FXCollections.observableArrayList();
    Connection connection = null;
    String query = "";
    Trainer trainer = null;
    PreparedStatement preparedStatement = null ;
    ResultSet resultSet = null ;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadData();
    }
    @FXML
    private void refreshTable() {
        try {
            TrainerList.clear();

            query = "SELECT * FROM trainers";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                TrainerList.add(new Trainer(
                        resultSet.getString("TName"),
                        resultSet.getString("TPhone"),
                        resultSet.getString("TUsername"),
                        resultSet.getString("TPassword"),
                        classNumber(resultSet.getString("TName")),
                        Trainer.calculateSalary(classNumber(resultSet.getString("TName")))));
                table.setItems(TrainerList);
            }



        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    private void loadData() {
        DatabaseConnection databaseConnection =new DatabaseConnection();
        connection = databaseConnection.getConnection();
        refreshTable();


        nameCol.setCellValueFactory(new PropertyValueFactory<Trainer, String>("Name"));
        passwordCol.setCellValueFactory(new PropertyValueFactory<Trainer, String>("password"));
        usernameCol.setCellValueFactory(new PropertyValueFactory<Trainer, String>("username"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<Trainer, String>("PhoneNumber"));
        classesCol.setCellValueFactory(new PropertyValueFactory<Trainer, String>("numclasses"));
        salaryCol.setCellValueFactory(new PropertyValueFactory<Trainer, String>("salary"));
    }
    @FXML
    public void clean(){
        nametf.setText(null);
        usernametf.setText(null);
        phonetf.setText(null);
        passwordtf.setText(null);
    }
    @Override
    @FXML
    public void ADD() {
       String name =  nametf.getText();
       String username = usernametf.getText();
       String phone = phonetf.getText();
       String password = passwordtf.getText();
       int classes = classNumber();
        if(name.isEmpty() || phone.isEmpty() || username.isEmpty() || password.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Fill All DATA");
            alert.showAndWait();
        }
        else {
            try {
                query = "Insert into trainers (TName, TUsername, TPassword, TPhone, TClasses, TSalary) values(?,?,?,?,?,?)";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, username);
                preparedStatement.setString(3, password);
                preparedStatement.setString(4, phone);
                preparedStatement.setInt(5, classes);
                preparedStatement.setDouble(6, Trainer.calculateSalary(classes));
                preparedStatement.execute();
                clean();
                refreshTable();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText(null);
                alert.setContentText("Member Added!!");
                alert.showAndWait();

            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    private int classNumber(){
        String name =  nametf.getText();
        String verifyLogin  = "select Count(SName) from sessions Where STrainer='"+ name+ "'";
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
    private int classNumber(String name){
        String verifyLogin  = "select Count(SName) from sessions Where STrainer='"+ name+ "'";
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
    public void REMOVE() {
        String name = nametf.getText();
        Trainer trainer  = table.getSelectionModel().getSelectedItem();
        if(trainer == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Select A Member!!");
            alert.showAndWait();
        }
        else{
            try {
                query = "Delete from trainers WHERE TName = '" + name + "'";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.execute();
                clean();
                refreshTable();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText(null);
                alert.setContentText("Coach Deleted!!");
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
        String username = usernametf.getText();
        String phone = phonetf.getText();
        String password = passwordtf.getText();
        Trainer trainer  = table.getSelectionModel().getSelectedItem();
        int classes = classNumber();
        if(name.isEmpty() || phone.isEmpty() || username.isEmpty() || password.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Fill All DATA");
            alert.showAndWait();
        }
        else if(trainer == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Select A Coach!!");
            alert.showAndWait();
        }
        else {
            try {
                query = "UPDATE trainers set TName=?, TUsername=?, TPassword=?, TPhone=?, TClasses=?, TSalary=? WHERE TName = '" + name + "'";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, username);
                preparedStatement.setString(3, password);
                preparedStatement.setString(4, phone);
                preparedStatement.setInt(5, classes);
                preparedStatement.setDouble(6, Trainer.calculateSalary(classes));
                preparedStatement.execute();
                clean();
                refreshTable();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText(null);
                alert.setContentText("Coach Updated!!");
                alert.showAndWait();

            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void showSelectedRow(){
        Trainer trainer  = table.getSelectionModel().getSelectedItem();
        if(trainer!=null){
            nametf.setText(trainer.getName());
            phonetf.setText(trainer.getPhoneNumber());
            usernametf.setText(trainer.getUsername());
            passwordtf.setText(trainer.getPassword());

        }
    }


    /////////////////////////////////////////////////////////////////
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
            HelloApplication.setScene((new FXMLLoader(getClass().getResource("Coach.fxml"))).load());
            HelloApplication.UpdateStage();
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
