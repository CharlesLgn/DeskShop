package com.deskshop.front.controllers;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

public class SignupController {
    @FXML
    private VBox vbox;

    @FXML
    private JFXTextField fieldPrenom;

    @FXML
    private JFXTextField fieldNom;

    @FXML
    private JFXTextField fieldMail;

    @FXML
    private JFXTextField fieldMDP;

    @FXML
    private JFXTextField fieldMDPconfirm;

    @FXML
    private JFXButton btCreer;

    @FXML
    private Hyperlink link;

    @FXML
    void Enter(KeyEvent event) {

    }

    @FXML
    void btCreerClick(ActionEvent event) {

    }
}

