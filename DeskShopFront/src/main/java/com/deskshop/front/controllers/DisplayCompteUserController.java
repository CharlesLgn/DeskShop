package com.deskshop.front.controllers;

import com.deskshop.common.constant.ServerConstant;
import com.deskshop.common.link.ClientInterface;
import com.deskshop.common.metier.Compte;
import com.deskshop.common.metier.Movement;
import com.deskshop.front.util.ControllerUtils;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.Serializable;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DisplayCompteUserController implements Initializable {

    @FXML
    private VBox vbox;

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
    private JFXButton editsoldeAnnuler;

    @FXML
    private JFXTextField jfxTextFieldTransfert;

    @FXML
    private VBox vBox;

    private Compte compte;
    List<Compte> comptes;

    public DisplayCompteUserController(Compte compte, List<Compte> comptes) {
        this.compte = compte;
        this.comptes = comptes;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {

            ClientInterface clientInterface = new ClientImpl();
            ServerConstant.SERVER.addObserver(clientInterface);

            // Que de l'interface ici
            hbox.setSpacing(30);
            this.accountName.setText(this.compte.getName());
            this.nom.setText(this.compte.getClient().getName() + " " + this.compte.getClient().getFirstName());
            this.mail.setText(this.compte.getClient().getMel());
            this.solde.setText(this.compte.getAmount() + "");
            // Dans cette form, on a pas besoin de l'édition du solde mais plutôt du transfert de fond
            // On retire donc les trois boutons non désirés
            this.hbox.getChildren().remove(this.editsolde);
            this.hbox.getChildren().remove(this.editsoldeValider);
            this.hbox.getChildren().remove(this.editsoldeAnnuler);
            // Ajout des nouveaux composants pour le transfert
            this.hbox.getChildren().add(new Label("Transfert :"));
            jfxTextFieldTransfert = new JFXTextField();
            jfxTextFieldTransfert.setPrefSize(120,30);
            this.hbox.getChildren().add(jfxTextFieldTransfert);
            this.hbox.getChildren().add(new Label("Vers :"));
            JFXComboBox jfxComboBoxAutresComptes = new JFXComboBox();
            List<Compte> compteList = new ArrayList<>(comptes);
            jfxComboBoxAutresComptes.setItems(FXCollections.observableArrayList(MyOtherAccounts(compteList)));
            jfxComboBoxAutresComptes.getSelectionModel().select(0);
            this.hbox.getChildren().add(jfxComboBoxAutresComptes);
            JFXButton jfxButtonTransfert = new JFXButton("Transférer");
            this.hbox.getChildren().add(jfxButtonTransfert);
            jfxButtonTransfert.setOnAction(event -> {
                if (this.jfxTextFieldTransfert.getText().matches("[0-9]+(\\.[0-9]{1,2})?") && !jfxComboBoxAutresComptes.getSelectionModel().isEmpty()) {
                    Transfert(Double.parseDouble(jfxTextFieldTransfert.getText()), this.compte, (Compte)jfxComboBoxAutresComptes.getValue());
                } else {
                    ControllerUtils.loadAlert("Echec du transfert de fonds", "Vérifiez que les champs sont correctement renseignés");
                }
            });
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
            vBox.getChildren().clear();
            List<Movement> movementList = ServerConstant.SERVER.findMovementByCompte(this.compte);
            for (Movement mov : movementList) {
                HBox hBox = new HBox();
                hBox.setSpacing(40);
                Label labeldate = new Label("Date :");
                Label labeldatedisplay = new Label(mov.getDate() + "");
                Label labelmontant = new Label("Montant :");
                Label labelmontantdisplay = new Label(mov.getAmount() + "");
                if (mov.getAmount() < 0) {
                    labelmontantdisplay.getStyleClass().add("movement-neg");
                } else {
                    labelmontantdisplay.getStyleClass().add("movement-pos");
                }
                hBox.getChildren().add(labeldate);
                hBox.getChildren().add(labeldatedisplay);
                hBox.getChildren().add(labelmontant);
                hBox.getChildren().add(labelmontantdisplay);
                hBox.setMargin(labeldate, new Insets(0, 0, 0, 20));
                this.vBox.getChildren().add(hBox);
            }
        }catch (Exception ex){
            ControllerUtils.loadAlert("Erreur générale", ex.toString());
        }
    }

    /**
     * Remove the currently used account from the list
     * @param comptesCombo List of accounts of the user
     * @return List of account without the one used
     */
    private List<Compte> MyOtherAccounts(List<Compte> comptesCombo){
        List<Compte> newCompteList = new ArrayList<>();
        for (Compte compte : comptesCombo) {
            if(compte.getName() != this.accountName.getText()){
                newCompteList.add(compte);
            }
        }

        return newCompteList;
    }

    private void Transfert(double somme, Compte compeGiver, Compte compteReceiver){
        try {
            // Je sais pas pourquoi mais Oui/Non ne marche pas ici...
            //YesNoDialogController yesNoDialogController = ControllerUtils.loadYesNoDialog();
            //if(yesNoDialogController.getResponse()) {
            if (compteReceiver != null){
                    if (compeGiver.getAmount() > somme) {
                        if (ServerConstant.SERVER.transfert(somme, compeGiver, compteReceiver)) {
                            ControllerUtils.loadAlert("Transfert de fonds effectué", "Le transfert de fonds a été effectué avec succès.");
                        } else {
                            ControllerUtils.loadAlert("Echec du transfert de fonds", "Le transfert de fonds a échoué.");
                        }
                    } else {
                        ControllerUtils.loadAlert("Echec du transfert de fonds", "Le transfert de fonds a échoué car le compte débité n'a pas suffisamment de ressources.");
                    }

            }else{
                ControllerUtils.loadAlert("Echec du transfert de fonds", "Veuillez sélectionner un compte pour le transfert.");
            }
            //}
        }catch (Exception ex){
            ControllerUtils.loadAlert("Echec du transfert de fonds", ex.toString());
        }
    }

    @FXML
    void editsoldeClick(ActionEvent event) {

    }

    @FXML
    void editsoldeValiderClick(ActionEvent event) {

    }

    @FXML
    void editsoldeAnnulerClick(ActionEvent event) {

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
            } else if (updateMsg.equals(compte)){
                compte = (Compte)updateMsg;
                solde.setText(compte.getAmount()+"");
            }
        }
    }
}
