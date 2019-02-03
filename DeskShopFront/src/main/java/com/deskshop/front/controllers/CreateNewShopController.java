package com.deskshop.front.controllers;

import com.deskshop.common.constant.ServerConstant;
import com.deskshop.front.util.ControllerUtils;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CreateNewShopController {

    private int UserId;

    public CreateNewShopController(int UserId){
        this.UserId = UserId;
    }

    @FXML
    private VBox vbox;

    @FXML
    private JFXTextField textfieldNomMagasin;

    @FXML
    private JFXButton bt_valider;

    @FXML
    void bt_validerClick(ActionEvent event) {
        try{
            YesNoDialogController yesNoDialogController = ControllerUtils.loadYesNoDialog();
            if(yesNoDialogController.getResponse()) {
                ServerConstant.SERVER.createShop(this.textfieldNomMagasin.getText(), this.UserId);
                ControllerUtils.loadAlert("Créer un nouveau magasin", "Votre magasin a été créé avec succès");
            }
        }catch(Exception ex){
            ControllerUtils.loadAlert("Echec de la création du magasin", ex.toString());
        }

        ((Stage) vbox.getScene().getWindow()).close();
    }
}
