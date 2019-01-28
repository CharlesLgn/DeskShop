package com.deskshop.front.controllers;

import com.deskshop.common.metier.Article;
import com.deskshop.front.util.ControllerUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.HashMap;
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

    private Article article;
    private HashMap<Article, Integer> panier;

    public DisplayArticleController(Article article, HashMap<Article, Integer> panier) {
        this.article = article;
        this.panier = panier;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //this.imgProduit.setImage(new Image(this.image));
        this.imgProduit.setImage(new Image("images/deskshop-logo.png"));
        this.lbNomProduit.setText(this.article.getName());
        this.lbPrix.setText(this.article.getPrice() + "Ø");
    }

    @FXML
    void linkAjouterPanierClick(ActionEvent event) {
        // Ajoute au panier
        //articleHashMap.put(this.article, 1);
        ControllerUtils.loadPopupArticle(article, panier);
    }
}
