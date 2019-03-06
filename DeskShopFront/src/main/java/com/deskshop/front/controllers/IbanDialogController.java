package com.deskshop.front.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

/**
 * Le controller de la boite de dialogue pour
 * payer sa commande
 * elle est composé d'un texte pour l'iban.
 * les rest du controlleur fonctionne comme @{@link YesNoDialogController}
 */
public class IbanDialogController {

    @FXML
    private JFXButton Yes;

    @FXML
    private JFXButton No;

    @FXML
    private JFXTextField textfieldIban;

    private boolean response;
    private String iban;

    @FXML
    void NoClick(ActionEvent event) {
        response = false;
        ((Stage) Yes.getScene().getWindow()).close();
    }

    @FXML
    void YesClick(ActionEvent event) {
        response = true;
        iban = textfieldIban.getText();
        ((Stage) Yes.getScene().getWindow()).close();
    }

    String getIban(){return iban;}

    boolean getResponse() {
        return response;
    }
}
