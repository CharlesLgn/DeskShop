package com.deskshop.front.controllers;

import com.deskshop.common.metier.Article;
import com.deskshop.front.util.ControllerUtils;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.net.URL;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.ResourceBundle;

public class ArticlePanierController implements Initializable {

    @FXML
    private JFXButton jfxButtonRetirer;

    @FXML
    private HBox hbox;

    @FXML
    private ImageView imgImageArticle;

    @FXML
    private Label lbNomArticle;

    @FXML
    private Label lbPrixArticle;

    @FXML
    private Label lbDesc;

    @FXML
    private Spinner<Integer> spinnerQuantite;

    private Article article;
    private int qte;

    public ArticlePanierController(Article article, int qte) {
        this.article = article;
        this.qte = qte;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            if(this.article.getPicture() != null){
                this.imgImageArticle.setImage(new Image(Paths.get(this.article.getPicture()).toUri().toURL().toExternalForm()));
            }else{
                this.imgImageArticle.setImage(new Image("images/deskshop-logo.png"));
            }
            this.lbNomArticle.setText(this.article.getName());
            this.lbPrixArticle.setText(this.article.getPrice() + "Ø");
            this.lbDesc.setText(this.article.getDesc());
            this.spinnerQuantite.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE, qte, 1));
            this.spinnerQuantite.valueProperty().addListener((observable, oldValue, newValue) -> {
               editerQuantite(newValue);
            });
        }catch (Exception ex){
            ControllerUtils.loadAlert("Erreur lors de la récupération des informations sur l'article", ex.toString());
        }
    }

    private void editerQuantite(int newQte){
        HashMap<Article, Integer> panier = PanierController.getPanier();
        panier.put(this.article, newQte);
        PanierController.setPanier(panier);
    }

    @FXML
    void jfxButtonRetirerClick(ActionEvent event) {
        HashMap<Article, Integer> panier = PanierController.getPanier();
        panier.remove(this.article);
        PanierController.setPanier(panier);
        hbox.setVisible(false);
    }
}
