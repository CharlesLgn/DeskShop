package com.deskshop.front.controllers;

import com.deskshop.common.metier.Article;
import com.deskshop.front.util.ControllerUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class ArticlePanierController implements Initializable {

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
            this.imgImageArticle.setImage(new Image(Paths.get(this.article.getPicture()).toUri().toURL().toExternalForm()));
            this.lbNomArticle.setText(this.article.getName());
            this.lbPrixArticle.setText(this.article.getPrice() + "Ø");
            this.lbDesc.setText(this.article.getDesc());
            this.spinnerQuantite.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE, qte, 1));
        }catch (Exception ex){
            ControllerUtils.loadAlert("Erreur lors de la récupération des informations sur l'article", ex.toString());
        }
    }
}
