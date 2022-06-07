package com.example.gymtopia;

import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;

import java.util.ResourceBundle;





public class Loading implements Initializable {
    @FXML
    private Circle c1;
    @FXML
    private Circle c2;
    @FXML
    private Circle c3;


    int rotate =0;
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Admin.getInstance("admin", "admin");
        setRotate(c1, 10, true, 360);
        setRotate2(c2, 18, true, 180);
        setRotate2(c3, 24, true, 145);


    }


    private void setRotate(Circle c, int duration, boolean reverse, int angle){
        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(duration), c);
        rotateTransition.setAutoReverse(reverse);
        rotateTransition.setByAngle(angle);
        rotateTransition.setDelay(Duration.seconds(0));
        rotateTransition.setRate(3);
        rotateTransition.setCycleCount(3);
        rotateTransition.play();
        rotateTransition.setOnFinished(actionEvent -> {
            try {
                HelloApplication.setScene((new FXMLLoader(getClass().getResource("login.fxml"))).load());
                HelloApplication.UpdateStage();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

    }
    private void setRotate2(Circle c, int duration, boolean reverse, int angle){
        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(duration), c);
        rotateTransition.setAutoReverse(reverse);
        rotateTransition.setByAngle(angle);
        rotateTransition.setDelay(Duration.seconds(0));
        rotateTransition.setRate(3);
        rotateTransition.setCycleCount(18);
        rotateTransition.play();


    }

}
