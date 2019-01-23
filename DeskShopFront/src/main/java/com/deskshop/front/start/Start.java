package com.deskshop.front.start;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Start extends Application {
    private static Stage prStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            //com.sun.javafx.util.Logging.getJavaFXLogger().setLevel(sun.util.logging.PlatformLogger.Level.OFF);
            prStage = primaryStage;
            System.out.println(new File("").getAbsolutePath());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/connexion.fxml"));
            Parent root = loader.load();
            //Font.loadFont("Roboto-Black.ttf", 10);
            //primaryStage.getIcons().add(new Image(getClass().getResource("/image/ediome.png").toString()));
            //primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Stage getPrimaryStage() {
        return prStage;
    }

    public static void setPrimaryStage(Stage prStage) {
        Start.prStage = prStage;
    }
}
