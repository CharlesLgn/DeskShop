package com.deskshop.front.controllers;

import com.deskshop.common.constant.ServerConstant;
import com.deskshop.common.link.ClientInterface;
import com.deskshop.common.metier.Compte;
import com.deskshop.common.metier.Movement;
import com.deskshop.front.util.ControllerUtils;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.sun.security.ntlm.Server;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.Serializable;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.ResourceBundle;

public class DisplayCompteController implements Initializable {

    @FXML
    private VBox vbox;

    @FXML
    private VBox vBox;

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
        buildcard();
        try {
            ClientInterface clientInterface = new ClientImpl();
            ServerConstant.SERVER.addObserver(clientInterface);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void buildcard(){
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

            vBox.setSpacing(20);
            generateMovements();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void generateMovements(){
        try {
            ControllerUtils.generateMovements(this.vBox, this.compte);
            }
        catch (Exception ex){
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
        try {
            YesNoDialogController yesNoDialogController = ControllerUtils.loadYesNoDialog();
            if(yesNoDialogController.getResponse()) {
                if (this.solde.getText().matches("[0-9]+(\\.[0-9]{1,2})?")) {
                    if (ServerConstant.SERVER.editSolde(Double.parseDouble(this.solde.getText()), this.compte)) {
                        ControllerUtils.loadAlert("Mise à jour du solde", "Le solde a été mis à jour");
                    } else {
                        ControllerUtils.loadAlert("Echec de la mise à jour du solde", "Veuillez entrer un chiffre cohérent");
                    }
                }
            }
        }catch (Exception ex){
            ControllerUtils.loadAlert("Echec de la mise à jour du solde", ex.toString());
            ex.printStackTrace();
        }
    }

    class ClientImpl extends UnicastRemoteObject implements ClientInterface, Serializable {
        ClientImpl() throws RemoteException {
            super();
        }

        private static final long serialVersionUID = 1L;

        @Override
        public void update(Object observable, Object updateMsg) {
           Platform.runLater(DisplayCompteController.this::generateMovements);
        }
    }
}
