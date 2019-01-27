package com.deskshop.front.controllers;

import com.deskshop.common.metier.Compte;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DisplayCompteUserController implements Initializable {

    @FXML
    private VBox vbox;

    @FXML
    private Label accountName;

    @FXML
    private HBox hbox;

    @FXML
    private JFXTextField solde;

    @FXML
    private HBox hbox1;

    @FXML
    private JFXButton bt_transfer;

    @FXML
    private JFXTextField soldeaTransferer;

    @FXML
    private JFXButton bt_Valider;

    @FXML
    private JFXButton bt_AnnulerTransf;

    @FXML
    private JFXComboBox<String> comptesUtilisateur;

    private Compte compte;
    List<Compte> comptes;

    public DisplayCompteUserController(Compte compte, List<Compte> comptes) {
        this.compte = compte;
        this.comptes = comptes;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.accountName.setText(this.compte.getName());
        this.solde.setText(this.compte.getAmount()+"");
        for (Compte compte : comptes) {
            this.comptesUtilisateur.setItems(FXCollections.observableArrayList(compte.getName()));
        }
    }

    @FXML
    void bt_AnnulerTransfClick(ActionEvent event) {

    }

    @FXML
    void bt_ValiderClick(ActionEvent event) {

    }

    @FXML
    void bt_transferClick(ActionEvent event) {

    }
}
