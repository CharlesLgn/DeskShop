package com.deskshop.front.controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class DisplayArticleController {


    @FXML
    private VBox vbox;

    @FXML
    private ImageView imgProduit;

    @FXML
    private Label lbNomProduit;

    @FXML
    private Label lbPrix;

    @FXML
    private Hyperlink linkAjouterPanier;

    @FXML
    void linkAjouterPanierClick(ActionEvent event) {

    }
}
