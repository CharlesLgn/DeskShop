package com.deskshop.front.controllers;

import com.deskshop.common.constant.ServerConstant;
import com.deskshop.front.util.ControllerUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ChoixController implements Initializable {

    @FXML
    private VBox vbox;

    @FXML
    private ImageView shop;

    @FXML
    private ImageView manageshop;

    @FXML
    private ImageView compte;

    @FXML
    private ImageView bank;

    private int userId;

    public ChoixController(int userId){
        this.userId = userId;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            if (!ServerConstant.SERVER.isBanker(this.userId)) {
                this.bank.setVisible(false);
            }
        }catch (Exception ex){
            ControllerUtils.loadAlert("Erreur générale", ex.toString());
        }
    }

    @FXML
    void bankClick(MouseEvent event) {
        try {
            ControllerUtils.loadDashBoard(userId, 3);
            ((Stage) vbox.getScene().getWindow()).close();
        }catch (Exception ex){
            ControllerUtils.loadAlert("Erreur générale", ex.toString());
        }
    }

    @FXML
    void compteClick(MouseEvent event) {
        try {
            ControllerUtils.loadDashBoard(userId, 2);
            ((Stage) vbox.getScene().getWindow()).close();
        }catch (Exception ex){
            ControllerUtils.loadAlert("Erreur générale", ex.toString());
        }
    }

    @FXML
    void manageshopClick(MouseEvent event) {
        try {
            ControllerUtils.loadDashBoard(userId, 1);
            ((Stage) vbox.getScene().getWindow()).close();
        }catch (Exception ex){
            ControllerUtils.loadAlert("Erreur générale", ex.toString());
        }
    }

    @FXML
    void shopClick(MouseEvent event) {
        try {
            ControllerUtils.loadDashBoard(userId, 0);
            ((Stage) vbox.getScene().getWindow()).close();
        }catch (Exception ex){
            ControllerUtils.loadAlert("Erreur générale", ex.toString());
        }
    }
}
