package com.example.gymtopia;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


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
            validateLogin();

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
        String verifyLogin  = "select Count(1) from members Where MUsername='"+ UsernameTextField.getText()+ "' AND MPassword = '"
                +PasswordTextField.getText()+"'";
        try{
            Statement statement = connection.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);
            while (queryResult.next()){
                if (queryResult.getInt(1)==1){
                    LoginText.setText("Welcome");
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
