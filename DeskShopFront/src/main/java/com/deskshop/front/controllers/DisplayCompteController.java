package com.deskshop.front.controllers;

import com.deskshop.common.metier.Compte;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class DisplayCompteController implements Initializable {

    @FXML
    private VBox vbox;

    @FXML
    private Label accountName;

    @FXML
    private Label nom;

    @FXML
    private Label prenom;

    @FXML
    private Label mail;

    @FXML
    private HBox hbox;

    @FXML
    private JFXTextField solde;

    @FXML
    private JFXButton editsolde;

    @FXML
    private JFXButton editsoldeValider;

    @FXML
    private JFXButton editsoldeAnnuler;

    private Compte compte;

    public DisplayCompteController(Compte compte) {
        this.compte = compte;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    @FXML
    void editsoldeAnnulerClick(ActionEvent event) {

    }

    @FXML
    void editsoldeClick(ActionEvent event) {

    }

    @FXML
    void editsoldeValiderClick(ActionEvent event) {

    }

}
