package com;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/sample.fxml"));//обязательно   /

        Scene scene = new Scene(root);
        scene.getStylesheets().add(0, "style.css");
        primaryStage.setTitle("Text finder");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String args[]) {
        launch(args);
    }
}