package com.example.gymtopia;

import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

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
        setRotate(c1, 10, true, 360);
        setRotate(c2, 18, true, 180);
        setRotate(c3, 24, true, 145);
    }

    private void setRotate(Circle c, int duration, boolean reverse, int angle){
        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(duration), c);
        rotateTransition.setAutoReverse(reverse);
        rotateTransition.setByAngle(angle);
        rotateTransition.setDelay(Duration.seconds(0));
        rotateTransition.setRate(3);
        rotateTransition.setCycleCount(18);
        rotateTransition.play();
    }

}
