package com.deskshop.front.controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class DisplayArticleController implements Initializable {


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

    private String image;
    private String nomProduit;
    private String prix;

    public DisplayArticleController(String image, String nomProduit, String prix) {
        this.image = image;
        this.nomProduit = nomProduit;
        this.prix = prix;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //this.imgProduit.setImage(new Image(this.image));
        this.imgProduit.setImage(new Image("images/deskshop-logo.png"));
        this.lbNomProduit.setText(this.nomProduit);
        this.lbPrix.setText(this.prix);
    }

    @FXML
    void linkAjouterPanierClick(ActionEvent event) {
        // Ajoute au panier
    }
}
