package com.deskshop.front.controllers;

import com.deskshop.common.constant.ServerConstant;
import com.deskshop.common.metier.Article;
import com.deskshop.common.metier.Magasin;
import com.deskshop.front.util.ControllerUtils;
import com.jfoenix.controls.JFXButton;
import javafx.beans.property.ObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
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
    
    private void regeneratePanier(){
        for (Node par: vbox.getChildren()) {
            if(par instanceof ScrollPane) {
                Node fp = ((ScrollPane) par).getContent();
                if (fp instanceof FlowPane){
                    List<Node> fpNodes = ((FlowPane) fp).getChildren();
                    for (Node fpDetails : fpNodes) {
                        if(fpDetails instanceof HBox){
                            List<Node> hboxDetails = ((HBox) fpDetails).getChildren();
                            for (Node hboxNodes: hboxDetails) {
                                if (hboxNodes instanceof GridPane){
                                    List<Node> gridviewDetails = ((GridPane) hboxNodes).getChildren();
                                    for (Node gridviewNodes: gridviewDetails){

                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
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
        try {
            //regeneratePanier();
            if(!panier.isEmpty()) {
                IbanDialogController ibanDialogController = ControllerUtils.loadIbanDialog();
                if (ibanDialogController.getResponse()) {
                    if (panier.entrySet().stream().allMatch(c -> c.getKey().getStock() > c.getValue())) {
                        boolean paid = ServerConstant.SERVER.paid(panier, nbUser, ibanDialogController.getIban(), magasin.getId());
                        if (paid) {
                            ControllerUtils.loadAlert("Commande effectuée", "Votre commande a été effectuée avec succès.");
                            dashboardController.viderPanier();
                        } else {
                            ControllerUtils.loadAlert("La commande ne peut pas s'effectuer", "Votre Iban n'existe pas");
                        }
                    } else {
                        ControllerUtils.loadAlert("La commande ne peut pas s'effectuer", "Le stock n'est pas suffisante pour honorer la demande");
                    }
                }
            }else{
                ControllerUtils.loadAlert("La commande ne peut pas s'effectuer", "Le panier est vide.");
            }
        }catch (Exception ex){
            ControllerUtils.loadAlert("Echec de la commande", ex.toString());
        }

        bt_closeClick(event);
    }
}
