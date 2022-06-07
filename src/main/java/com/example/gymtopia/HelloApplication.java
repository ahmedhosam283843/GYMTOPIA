package com.example.gymtopia;
import com.sun.javafx.application.LauncherImpl;
import javafx.application.Application;
import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.time.LocalTime;
import java.util.Timer;


public class HelloApplication extends Application {
    private static Scene scene;
    private static  Stage stg;
    public static Boolean isAdmin = false;
    public static void setScene(Parent p) {
        scene = new Scene(p);
    }

    public static void UpdateStage(){
        stg.setScene(scene);
        stg.show();
    }

    @Override
    public void start(Stage stage) throws IOException {
        stg= stage;
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Loading.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stg.initStyle(StageStyle.UNDECORATED);
        stg.setScene(scene);
        stg.show();
    }



    public static void main(String[] args) {
        launch(args);
    }
}