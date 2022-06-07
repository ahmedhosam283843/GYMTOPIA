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


public class MemberController implements Initializable, Utility {
    @FXML
    private TableView<Members> table;
    @FXML
    private TextField nametf;
    @FXML
    private TextField phonetf;
    @FXML
    private TextField weighttf;
    @FXML
    private TextField heighttf;
    @FXML
    private DatePicker datepicker;
    @FXML
    private ChoiceBox coachcombo;
    @FXML
    private ChoiceBox classcombo;
    @FXML
    private TableColumn<Members, String> nameCol;
    @FXML
    private TableColumn<Members, String> numCol;
    @FXML
    private TableColumn<Members, String> bmiCol;
    @FXML
    private TableColumn<Members, String> coachCol;
    @FXML
    private TableColumn<Members, String> classCol;
    @FXML
    private TableColumn<Members, String> dateCol;
    @FXML
    private TableColumn<Members, String> billCol;

    ObservableList<Members> MemberList = FXCollections.observableArrayList();
    Connection connection = null;
    String query = "";
    Members member = null;
    PreparedStatement preparedStatement = null ;
    ResultSet resultSet = null ;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadData();
    }

    @FXML
    private void refreshTable() {
        try {
            MemberList.clear();

            query = "SELECT * FROM `members`";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                MemberList.add(new Members(
                        resultSet.getString("MName"),
                        resultSet.getString("MPhone"),
                        resultSet.getDouble("MBMI"),
                        resultSet.getString("TName"),
                        resultSet.getString("MClass"),
                        resultSet.getDate("MExpiry"),
                        resultSet.getDouble("MBill")));
                table.setItems(MemberList);
            }



        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    private void loadData() {
        DatabaseConnection databaseConnection =new DatabaseConnection();
        connection = databaseConnection.getConnection();
        coachCombo(connection);
        classCombo(connection);
        refreshTable();


        nameCol.setCellValueFactory(new PropertyValueFactory<Members, String>("Name"));
        numCol.setCellValueFactory(new PropertyValueFactory<Members, String>("PhoneNumber"));
        bmiCol.setCellValueFactory(new PropertyValueFactory<Members, String>("bmi"));
        coachCol.setCellValueFactory(new PropertyValueFactory<Members, String>("trainerName"));
        classCol.setCellValueFactory(new PropertyValueFactory<Members, String>("sessionName"));
        dateCol.setCellValueFactory(new PropertyValueFactory<Members, String>("expiry"));
        billCol.setCellValueFactory(new PropertyValueFactory<Members, String>("bill"));

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
            coachcombo.setItems(FXCollections.observableArrayList(coaches));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private void classCombo(Connection connection)  {
        try {
            query = "SELECT SName FROM `sessions`";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            ArrayList<String> sessions = new ArrayList<>();
            while (resultSet.next()) {
                sessions.add(resultSet.getString("SName"));
            }
            classcombo.setItems(FXCollections.observableArrayList(sessions));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    @FXML
    public void ADD() {
        String name = nametf.getText();
        String phone = phonetf.getText();
        String weight = weighttf.getText();
        String height = heighttf.getText();
        String coach  = String.valueOf(coachcombo.getValue());
        String session  = String.valueOf(classcombo.getValue());
        LocalDate date = datepicker.getValue();
    if(name.isEmpty() || phone.isEmpty() || weight.isEmpty() || height.isEmpty() || coach.isEmpty() || session.isEmpty() || date == null){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText("Please Fill All DATA");
        alert.showAndWait();
    }
    else{
        try {
            query = "Insert into members (MName, MPhone, MBMI, TName, MClass, MExpiry, MBill) values(?,?,?,?,?,?,?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, phone);
            preparedStatement.setDouble(3, Math.round(Members.CalculateBMI(Double.parseDouble(weight), Double.parseDouble(height))));
            preparedStatement.setString(4, coach);
            preparedStatement.setString(5, session);
            preparedStatement.setDate(6, Date.valueOf(date));
            preparedStatement.setDouble(7, (new Membership(Date.valueOf(date), (new Classes("zomba", null, "", null)))).getBill());
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

    @FXML
    private void clean(){
        nametf.setText(null);
        weighttf.setText(null);
        phonetf.setText(null);
        heighttf.setText(null);
        coachcombo.setValue(null);
        classcombo.setValue(null);
        datepicker.setValue(null);
    }

    @Override
    @FXML
    public void REMOVE() {
        String name = nametf.getText();
        Members member  = table.getSelectionModel().getSelectedItem();
        if(member == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Select A Member!!");
            alert.showAndWait();
        }
        else{
            try {
                query = "Delete from members WHERE MName = '" + name + "'";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.execute();
                clean();
                refreshTable();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText(null);
                alert.setContentText("Member Deleted!!");
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
        String name = nametf.getText();
        String phone = phonetf.getText();
        String weight = weighttf.getText();
        String height = heighttf.getText();
        String coach  = String.valueOf(coachcombo.getValue());
        String session  = String.valueOf(classcombo.getValue());
        LocalDate date = datepicker.getValue();
        Members member  = table.getSelectionModel().getSelectedItem();
        if(name.isEmpty() || phone.isEmpty() || weight.isEmpty() || height.isEmpty() || coach.isEmpty() || session.isEmpty() || date == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Fill All DATA");
            alert.showAndWait();
        }
        else if(member == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Select A Member!!");
            alert.showAndWait();
        }
        else{
            try {
                query = "UPDATE members SET MName=?, MPhone=?, MBMI=?, TName = ?, MClass= ?, MExpiry= ?, MBill = ? WHERE MName = '" + name + "'";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, phone);
                preparedStatement.setDouble(3, Math.round(Members.CalculateBMI(Double.parseDouble(weight), Double.parseDouble(height))));
                preparedStatement.setString(4, coach);
                preparedStatement.setString(5, session);
                preparedStatement.setDate(6, Date.valueOf(date));
                preparedStatement.setDouble(7, (new Membership(Date.valueOf(date), (new Classes("zomba", null, "", null)))).getBill());
                preparedStatement.execute();
                clean();
                refreshTable();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText(null);
                alert.setContentText("Member Updated!!");
                alert.showAndWait();

            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

    }
    @FXML
    public void showSelectedRow(){
        Members member  = table.getSelectionModel().getSelectedItem();
        if(member!=null){
            nametf.setText(member.getName());
            weighttf.setText(String.valueOf(member.getWeight()));
            phonetf.setText(member.getPhoneNumber());
            heighttf.setText(String.valueOf(member.getHeight()));
            coachcombo.setValue(member.getTrainerName());
            classcombo.setValue(member.getSessionName());
            datepicker.setValue(LocalDate.parse( new SimpleDateFormat("yyyy-MM-dd").format(member.getExpiry()) ));
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
