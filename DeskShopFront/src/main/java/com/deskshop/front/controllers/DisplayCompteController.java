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
    private Label lb_iban;

    private Compte compte;

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
            ControllerUtils.loadAlert("Erreur générale", ex.toString());
        }
    }

    public void buildcard(){
        try {
            this.accountName.setText(this.compte.getName());
            this.nom.setText(this.compte.getClient().getName() + " " + this.compte.getClient().getFirstName());
            this.mail.setText(this.compte.getClient().getMel());
            this.solde.setText(this.compte.getAmount() + "");
            this.lb_iban.setText(this.lb_iban.getText() + " " + compte.getIban());
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
            ControllerUtils.loadAlert("Erreur générale", ex.toString());
        }
    }

    private void generateMovements(){
        try {
            ControllerUtils.generateMovements(this.vBox, this.compte);
            }
        catch (Exception ex){
            ControllerUtils.loadAlert("Erreur générale", ex.toString());
        }
    }

    @FXML
    void editsoldeClick(ActionEvent event) {
            this.solde.setDisable(false);
            this.editsoldeValider.setVisible(true);
    }

    @FXML
    void editsoldeValiderClick(ActionEvent event) {
        try {
            // Ici non plus Oui/non marche pas...
            //YesNoDialogController yesNoDialogController = ControllerUtils.loadYesNoDialog();
            //if(yesNoDialogController.getResponse()) {
                if (this.solde.getText().matches("[0-9]+(\\.[0-9]{1,2})?")) {
                    if (ServerConstant.SERVER.editSolde(Double.parseDouble(this.solde.getText()), this.compte)) {
                        ControllerUtils.loadAlert("Mise à jour du solde", "Le solde a été mis à jour");
                        this.solde.setDisable(true);
                        this.editsoldeValider.setVisible(false);
                    } else {
                        ControllerUtils.loadAlert("Echec de la mise à jour du solde", "Veuillez entrer un chiffre cohérent");
                    }
                }else{
                    ControllerUtils.loadAlert("Echec de la mise à jour du solde", "Veuillez entrer un nombre cohérent");
                }
            //}
        }catch (Exception ex){
            ControllerUtils.loadAlert("Echec de la mise à jour du solde", ex.toString());
        }
    }

    class ClientImpl extends UnicastRemoteObject implements ClientInterface, Serializable {
        ClientImpl() throws RemoteException {
            super();
        }

        private static final long serialVersionUID = 1L;

        @Override
        public void update(Object observable, Object updateMsg) {
            if(updateMsg instanceof List){
                if(((List)updateMsg).get(0) instanceof Compte){
                    if(((List)updateMsg).contains(compte)){
                        Platform.runLater(()->{
                            generateMovements();
                            List<Compte> compteList = (List<Compte>) updateMsg;
                            compte = compteList.get(compteList.indexOf(compte));
                            solde.setText(compte.getAmount()+"");
                        });
                    }
                }
            }
        }
    }
}
