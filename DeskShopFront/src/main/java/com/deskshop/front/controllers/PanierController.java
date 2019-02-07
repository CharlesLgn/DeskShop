package com.deskshop.front.controllers;

import com.deskshop.common.constant.ServerConstant;
import com.deskshop.common.metier.Article;
import com.deskshop.common.metier.Magasin;
import com.deskshop.front.util.ControllerUtils;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
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

    private int nbUser;
    private Magasin magasin;
    private HashMap<Article, Integer> panier;
    private DashboardController dashboardController;

    public PanierController(HashMap<Article, Integer> panier, int nbUser, Magasin magasin, DashboardController dashboardController) {
        this.nbUser = nbUser;
        this.magasin = magasin;
        this.panier = panier;
        this.dashboardController = dashboardController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        scrollpane.setFitToHeight(true);
        scrollpane.setFitToWidth(true);
        FlowPane flowPane = new FlowPane();
        flowPane.setAlignment(Pos.CENTER);
        flowPane.setVgap(30);
        for (Map.Entry<Article, Integer> entry:panier.entrySet()){
            System.out.println(entry.getKey() + " = " + entry.getValue());
            Pane pane = ControllerUtils.loadArticlePanier(((Article) entry.getKey()), ((int) entry.getValue()));
            flowPane.getChildren().add(pane);
        }

        scrollpane.setContent(flowPane);
    }

    @FXML
    void bt_closeClick(ActionEvent event) {
        ((Stage) vbox.getScene().getWindow()).close();
    }

    @FXML
    void bt_commanderClick(ActionEvent event) {
        // Commander les articles sélectionnés
        try {
            YesNoDialogController yesNoDialogController = ControllerUtils.loadYesNoDialog();
            if(yesNoDialogController.getResponse()) {
                if (panier.entrySet().stream().allMatch(c -> c.getKey().getStock() > c.getValue())){
                ServerConstant.SERVER.paid(panier, nbUser, magasin.getId());
                ControllerUtils.loadAlert("Commande effectuée", "Votre commande a été effectuée avec succès.");
                dashboardController.viderPanier();
                }else{
                    ControllerUtils.loadAlert("La commande ne peut pas s'effectuer", "Le stock n'est pas suffisante pour honorer la demande");
                }
            }
        }catch (Exception ex){
            ControllerUtils.loadAlert("Echec de la commande", ex.toString());
        }

        bt_closeClick(event);
    }
}
