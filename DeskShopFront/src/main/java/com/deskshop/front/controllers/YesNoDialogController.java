package com.deskshop.front.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

/**
 * Le controller de la boite de dialogue pour
 * payer sa commande
 * elle est composé d'un texte pour l'iban.
 * les rest du controlleur fonctionne comme @{@link YesNoDialogController}
 */
public class YesNoDialogController {

    @FXML
    private JFXButton Yes;

    @FXML
    private JFXButton No;

    private boolean response;

    @FXML
    void NoClick(ActionEvent event) {
        response = false;
        ((Stage) Yes.getScene().getWindow()).close();
    }

    @FXML
    void YesClick(ActionEvent event) {
        response = true;
        ((Stage) Yes.getScene().getWindow()).close();
    }

    boolean getResponse() {
        return response;
    }
}
