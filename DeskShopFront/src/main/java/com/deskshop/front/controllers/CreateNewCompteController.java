package com.deskshop.front.controllers;

import com.deskshop.common.constant.ServerConstant;
import com.deskshop.common.metier.Person;
import com.deskshop.front.util.ControllerUtils;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CreateNewCompteController implements Initializable {
    @FXML
    private VBox vbox;

    @FXML
    private VBox vboxBody;

    @FXML
    private JFXTextField textfieldNomCompte;

    @FXML
    private JFXButton bt_valider;

    @FXML
    private Button bt_close;

    JFXTextField amount = new JFXTextField();
    JFXComboBox jfxComboBoxUsers = new JFXComboBox();

    private int nbUser;
    private boolean allOrMy;

    public CreateNewCompteController(int nbUser, boolean allOrMy){
        this.nbUser = nbUser;
        this.allOrMy = allOrMy;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            if (!allOrMy) {
                this.vbox.setPrefHeight(350);
                amount.setPrefWidth(300);
                amount.setMaxWidth(amount.getPrefWidth());
                amount.setPromptText("Solde du compte");
                amount.setLabelFloat(true);
                jfxComboBoxUsers.setPrefWidth(300);
                jfxComboBoxUsers.setPromptText("Sélectionnez la personne");
                jfxComboBoxUsers.setLabelFloat(true);
                List<Person> users = ServerConstant.SERVER.findAllUsers();
                jfxComboBoxUsers.setItems(FXCollections.observableArrayList(users));
                jfxComboBoxUsers.getSelectionModel().select(0);
                vboxBody.getChildren().addAll(amount, jfxComboBoxUsers);
            }
        } catch (Exception ex) {
            ControllerUtils.loadAlert("Echec de la récupération des utilisateurs", ex.toString());
        }
    }
    @FXML
    void bt_validerClick(ActionEvent event) {
        try{
            YesNoDialogController yesNoDialogController = ControllerUtils.loadYesNoDialog();
            if (yesNoDialogController.getResponse()) {
                if (allOrMy) {
                    ServerConstant.SERVER.createCompte(this.textfieldNomCompte.getText(), 0, nbUser);
                } else {
                    if(amount.getText().matches("[0-9]+") && textfieldNomCompte.getText().matches("(.)+")) {
                    ServerConstant.SERVER.createCompte(this.textfieldNomCompte.getText(),
                            Double.parseDouble(this.amount.getText()), ((Person) this.jfxComboBoxUsers.getValue()).getId());
                    }else{
                        ControllerUtils.loadAlert("Créer un nouveau compte", "Veuillez renseigner correctement les champs.");
                    }
                }
                ControllerUtils.loadAlert("Créer un nouveau compte", "Votre compte a été créé avec succès");
                ((Stage) vbox.getScene().getWindow()).close();
            }
        }catch(Exception ex) {
            ControllerUtils.loadAlert("Echec de la création du compte", ex.toString());
        }
    }


    @FXML
    void bt_close_action(ActionEvent event) {
        ((Stage) vbox.getScene().getWindow()).close();
    }
}
