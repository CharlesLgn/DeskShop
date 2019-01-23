package com.deskshop.front.start;

import com.deskshop.front.util.ControllerUtils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

public class Start extends Application {
    private static Stage prStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/connexion.fxml"));
            ControllerUtils.loadStart(loader);
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
