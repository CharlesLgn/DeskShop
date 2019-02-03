package com.deskshop.front.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AlertController implements Initializable {

    @FXML
    private Label lb_head;

    @FXML
    private Label lb_body;

    @FXML
    private JFXButton bt_Ok;

    private String titre;
    private String message;

    public AlertController(String titre, String message){
        this.message = message;
        this.titre = titre;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.lb_head.setText(titre);
        this.lb_body.setText(message);
    }

    @FXML
    void bt_Ok(ActionEvent event) {
        ((Stage) lb_head.getScene().getWindow()).close();
    }

}
