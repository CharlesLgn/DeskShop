package com.deskshop.front.controllers;

import com.deskshop.common.constant.ServerConstant;
import com.deskshop.front.util.ControllerUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

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
    void vboxEnter(MouseEvent event) {
        Scene sc = vbox.getScene();
        sc.setFill(Color.TRANSPARENT);
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

    @FXML
    void shopEnter(MouseEvent event) {
        animation(shop, true);
    }

    @FXML
    void shopExit(MouseEvent event) {
        animation(shop, false);
    }

    @FXML
    void manageshopEnter(MouseEvent event) {
        animation(manageshop, true);
    }

    @FXML
    void manageshopExit(MouseEvent event) {
        animation(manageshop, false);
    }

    @FXML
    void compteEnter(MouseEvent event) {
        animation(compte, true);
    }

    @FXML
    void compteExit(MouseEvent event) {
        animation(compte, false);
    }

    @FXML
    void bankEnter(MouseEvent event) {
        animation(bank, true);
    }

    @FXML
    void bankExit(MouseEvent event) {
        animation(bank, false);
    }

    private void animation(ImageView imgToAnimate, boolean expand){
        Timer animTimer = new Timer();
        animTimer.scheduleAtFixedRate(new TimerTask() {
            int i = 0;
            @Override
            public void run() {
                if(i<25){
                    imgToAnimate.setFitHeight(expand ? imgToAnimate.getFitHeight() + 1 : imgToAnimate.getFitHeight() - 1);
                    imgToAnimate.setFitWidth(expand ? imgToAnimate.getFitWidth() + 1 : imgToAnimate.getFitWidth() - 1);
                }else{
                    this.cancel();
                }
                i++;
            }
        }, 0,5);
    }
}
