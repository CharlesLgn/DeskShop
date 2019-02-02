package com.deskshop.front.controllers;

import com.deskshop.common.constant.ServerConstant;
import com.deskshop.front.util.ControllerUtils;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CreateNewCompteController {
    @FXML
    private VBox vbox;

    @FXML
    private JFXTextField textfieldNomCompte;

    @FXML
    private JFXButton bt_valider;

    private int nbUser;
    public CreateNewCompteController(int nbUser){
        this.nbUser = nbUser;
    }

    @FXML
    void bt_validerClick(ActionEvent event) {
        try{
            YesNoDialogController yesNoDialogController = ControllerUtils.loadYesNoDialog();
            if(yesNoDialogController.getResponse()) {
                ServerConstant.SERVER.createCompte(this.textfieldNomCompte.getText(), 0, nbUser);
                ControllerUtils.loadAlert("Créer un nouveau compte", "Votre compte a été créé avec succès");
            }
        }catch(Exception ex){
            ControllerUtils.loadAlert("Echec de la création du compte", ex.toString());
            ex.printStackTrace();
        }

        ((Stage) vbox.getScene().getWindow()).close();
    }
}
