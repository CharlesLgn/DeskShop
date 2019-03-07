package com.deskshop.front.controllers;

import com.deskshop.common.constant.EStatusPaiement;
import com.deskshop.common.constant.ServerConstant;
import com.deskshop.common.metier.Article;
import com.deskshop.common.metier.Magasin;
import com.deskshop.front.util.ControllerUtils;
import com.deskshop.utils.PayementUtils;
import com.jfoenix.controls.JFXButton;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class PanierController implements Initializable {

    @FXML
    private VBox vbox;

    @FXML
    private JFXButton bt_close;

    @FXML
    private JFXButton bt_commander;

    @FXML
    private ScrollPane scrollpane;

    @FXML
    private Label lb_total;

    private int nbUser;
    private Magasin magasin;

    public static HashMap<Article, Integer> getPanier() {
        return panier;
    }

    public static void setPanier(HashMap<Article, Integer> panier) {
        panier = panier;
    }

    private static HashMap<Article, Integer> panier;
    private DashboardController dashboardController;

    public PanierController(HashMap<Article, Integer> panier, int nbUser, Magasin magasin, DashboardController dashboardController) {
        this.nbUser = nbUser;
        this.magasin = magasin;
        this.panier = panier;
        this.dashboardController = dashboardController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                displayPanier(false);
            }
        }.start();
    }

    private void displayPanier(boolean b) {
        if (scrollpane.getContent() == null || panier.entrySet().size() != ((FlowPane)scrollpane.getContent()).getChildren().size()) {
            scrollpane.setFitToHeight(true);
            scrollpane.setFitToWidth(true);
            FlowPane flowPane = new FlowPane();
            flowPane.setAlignment(Pos.CENTER);
            flowPane.setVgap(30);
            for (Map.Entry<Article, Integer> entry : panier.entrySet()) {
                Pane pane = ControllerUtils.loadArticlePanier(((Article) entry.getKey()), ((int) entry.getValue()));
                flowPane.getChildren().add(pane);
            }

            scrollpane.setContent(flowPane);
        }

        lb_total.setText(PayementUtils.getTotal(panier) + "");
    }

    @FXML
    void bt_closeClick(ActionEvent event) {
        ((Stage) vbox.getScene().getWindow()).close();
    }

    @FXML
    void bt_commanderClick(ActionEvent event) {
        try {
            if(!panier.isEmpty()) {
                IbanDialogController ibanDialogController = ControllerUtils.loadIbanDialog();
                if (ibanDialogController.getResponse()) {
                    if (panier.entrySet().stream().allMatch(c -> c.getKey().getStock() > c.getValue())) {
                        EStatusPaiement paiement = ServerConstant.SERVER.paid(panier, nbUser, ibanDialogController.getIban(), magasin.getId());
                        if (paiement.equals(EStatusPaiement.SUCCESS)) {
                            ControllerUtils.loadAlert("Commande effectuée", "Votre commande a été effectuée avec succès.");
                            dashboardController.viderPanier();
                        } else if (paiement.equals(EStatusPaiement.IBAN_ERROR)) {
                            ControllerUtils.loadAlert("La commande ne peut pas s'effectuer", "Votre Iban n'existe pas");
                        } else if (paiement.equals(EStatusPaiement.MONEY_ERROR)) {
                            ControllerUtils.loadAlert("La commande ne peut pas s'effectuer", "argent insuffisant");
                        } else {
                            ControllerUtils.loadAlert("La commande ne peut pas s'effectuer", "Erreur interne");
                        }
                    } else {
                        ControllerUtils.loadAlert("La commande ne peut pas s'effectuer", "Le stock n'est pas suffisante pour honorer la demande");
                    }
                }
            }else{
                ControllerUtils.loadAlert("La commande ne peut pas s'effectuer", "Le panier est vide.");
            }
        } catch (Exception ex) {
            ControllerUtils.loadAlert("Echec de la commande", ex.toString());
        }

        bt_closeClick(event);
    }
}
