package com.example.gymtopia;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Objects;


public class Login {
    @FXML
     Button MemberBtn;
    @FXML
    private Button TrainerBtn;
    @FXML
    private TextField UsernameTextField;
    @FXML
    private PasswordField PasswordTextField;
    @FXML
    private Label admin;
    @FXML
    private Label LoginText;


    public void MemberBtnOnAction(ActionEvent e) throws IOException {
        if(UsernameTextField.getText().isBlank()==false && PasswordTextField.getText().isBlank()==false){
            Admin admin = Admin.getInstance("admin", "admin");
            boolean flag = (Objects.equals(UsernameTextField.getText(), admin.username))&& (Objects.equals(admin.password, PasswordTextField.getText()));
            if(flag){
                HelloApplication.isAdmin = true;
                HelloApplication.setScene((new FXMLLoader(getClass().getResource("Member.fxml"))).load());
                HelloApplication.UpdateStage();
            }
            else validateLogin();

        }
        else{
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle("Login Failed");
            al.setContentText("Enter A user name and a Password");
            al.showAndWait();
        }
    }

    public void validateLogin(){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getConnection();
        String verifyLogin  = "select Count(1) from trainers Where TUsername='"+ UsernameTextField.getText()+ "' AND TPassword = '"
                +PasswordTextField.getText()+"'";
        try{
            Statement statement = connection.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);
            while (queryResult.next()){
                if (queryResult.getInt(1)==1){
                    HelloApplication.setScene((new FXMLLoader(getClass().getResource("Member.fxml"))).load());
                    HelloApplication.UpdateStage();
                    HelloApplication.isAdmin = false;
                }
                else{
                    Alert al = new Alert(Alert.AlertType.ERROR);
                    al.setTitle("Login Failed");
                    al.setContentText("Enter a valid user name and password");
                    al.showAndWait();


                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}
