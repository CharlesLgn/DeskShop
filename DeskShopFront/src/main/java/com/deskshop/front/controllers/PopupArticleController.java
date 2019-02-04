package com.deskshop.front.controllers;

import com.deskshop.common.constant.ServerConstant;
import com.deskshop.common.metier.Article;
import com.deskshop.front.util.ControllerUtils;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.ResourceBundle;

public class PopupArticleController implements Initializable {

    @FXML
    private HBox hbox;

    @FXML
    private JFXButton btQuitter;

    @FXML
    private ImageView imgProduit;

    @FXML
    private Label lbNomProduit;

    @FXML
    private Label lb_Prix;

    @FXML
    private Label lb_desc;

    @FXML
    private Hyperlink linkAddPanier;

    @FXML
    private HBox hboxbottom;

    @FXML
    private VBox vboxbottom;

    @FXML
    private Spinner<Integer> spinnerQte;

    private Article article;
    HashMap<Article, Integer> panier;
    private boolean modifyArticle;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            this.lbNomProduit.setText(this.article.getName());
            this.lb_Prix.setText(this.article.getPrice() + "");
            this.lb_desc.setText(this.article.getDesc());
            //this.imgProduit.setImage(new Image("images/deskshop-logo.png"));
            try {
                this.imgProduit.setImage(new Image(Paths.get(this.article.getPicture()).toUri().toURL().toExternalForm()));
            }catch (Exception ignore){
                this.imgProduit.setImage(new Image("images/deskshop-logo.png"));
            }
            //this.imgProduit.setImage(new Image(Paths.get(this.article.getPicture()).toUri().toURL().toExternalForm()));
            spinnerQte.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE, 1, 1));
            if (modifyArticle) {
                // Modifier l'article
                this.spinnerQte.setVisible(false);
                Hyperlink deleteArticle = new Hyperlink("Supprimer l'article");
                deleteArticle.setOnAction(event -> {
                    deleteArticle(this.article);
                });

                this.vboxbottom.getChildren().add(deleteArticle);
                this.linkAddPanier.setText("Modifier l'article");
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public PopupArticleController(Article article, HashMap<Article, Integer> panier, boolean modifyArticle) {
        this.article = article;
        this.panier = panier;
        this.modifyArticle = modifyArticle;
    }

    @FXML
    void imgProduitEnter(MouseEvent event) {

    }

    @FXML
    void imgProduitExited(MouseEvent event) {

    }

    void deleteArticle(Article article){
        try {
            YesNoDialogController yesNoDialogController = ControllerUtils.loadYesNoDialog();
            if(yesNoDialogController.getResponse()) {
                ServerConstant.SERVER.deleteArticle(article);
                ControllerUtils.loadAlert("Article supprimé", "L'article a été supprimé avec succès.");
            }
        }catch (Exception ex){
            ControllerUtils.loadAlert("Echec de la suppression de l'article", ex.toString());
        }
        ((Stage) hbox.getScene().getWindow()).close();
    }

    @FXML
    void linkAddPanierClick(ActionEvent event) {
        if(modifyArticle){
            ControllerUtils.loadCreateNewArticle(this.article);
            btQuitterClick(event);
        }else{
            panier.put(article, (panier.get(article) == null ?  0:panier.get(article)) + this.spinnerQte.getValue());
        }
    }

    @FXML
    void btQuitterClick(ActionEvent event) {
        ((Stage) hbox.getScene().getWindow()).close();
    }
}
