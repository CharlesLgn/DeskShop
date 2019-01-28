package com.deskshop.front.controllers;

import com.deskshop.common.constant.ServerConstant;
import com.deskshop.common.metier.Compte;
import com.deskshop.common.metier.Movement;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.sun.security.ntlm.Server;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.List;
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
    private double soldeMemoire = 0;

    public DisplayCompteController(Compte compte) {
        this.compte = compte;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            this.accountName.setText(this.compte.getName());
            this.nom.setText(this.compte.getClient().getName());
            this.prenom.setText(this.compte.getClient().getFirstName());
            this.mail.setText(this.compte.getClient().getMel());
            this.solde.setText(this.compte.getAmount() + "");
            this.solde.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue,
                                    String newValue) {
                    if (!newValue.matches("[\\d.]")) {
                        solde.setText(newValue.replaceAll("[^\\d.]", ""));
                    }
                }
            });

            VBox vBox = new VBox();
            vBox.setFillWidth(true);
            vBox.setSpacing(15);
            List<Movement> movementList = ServerConstant.SERVER.findMovementByCompte(this.compte);
            for (Movement mov:movementList) {
                HBox hBox = new HBox();
                hbox.setSpacing(10);
                Label labeldate = new Label("Date :");
                Label labeldatedisplay = new Label(mov.getDate()+"");
                Label labelmontant = new Label("Montant :");
                Label labelmontantdisplay = new Label(mov.getAmount()+"");
                if(mov.getAmount() < 0){
                    labelmontantdisplay.setTextFill(Color.RED);
                }else{
                    labelmontantdisplay.setTextFill(Color.LIGHTGREEN);
                }
                hbox.getChildren().add(labeldate);
                hbox.getChildren().add(labeldatedisplay);
                hbox.getChildren().add(labelmontant);
                hbox.getChildren().add(labelmontantdisplay);
                vBox.getChildren().add(hbox);
            }

            vbox.getChildren().add(vBox);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @FXML
    void editsoldeAnnulerClick(ActionEvent event) {
        this.solde.setDisable(true);
        this.editsoldeValider.setVisible(false);
        this.editsoldeAnnuler.setVisible(false);
        this.solde.setText(soldeMemoire+"");
    }

    @FXML
    void editsoldeClick(ActionEvent event) {
            soldeMemoire = Double.parseDouble(this.solde.getText());
            this.solde.setDisable(false);
            this.editsoldeValider.setVisible(true);
            this.editsoldeAnnuler.setVisible(true);
    }

    @FXML
    void editsoldeValiderClick(ActionEvent event) {
        // Valide le changement
        if(this.solde.getText().matches("[0-9]+(\\.[0-9]{1,2})?")){
            // la forme est bonne
        }
    }

}
