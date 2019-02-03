package com.deskshop.front.controllers;

import com.deskshop.common.constant.ServerConstant;
import com.deskshop.common.metier.Article;
import com.deskshop.common.metier.Magasin;
import com.deskshop.front.util.ControllerUtils;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateNewArticleController implements Initializable {

    @FXML
    private VBox vbox;

    @FXML
    private Label lbNom;

    @FXML
    private JFXTextField nomArticle;

    @FXML
    private JFXTextField prixArticle;

    @FXML
    private JFXTextArea DescArticle;

    @FXML
    private JFXButton imgArticle;

    @FXML
    private JFXButton validerArticle;

    @FXML
    private JFXButton close;

    private Magasin magasin;
    private Article article;
    public CreateNewArticleController(Magasin magasin){
        this.magasin = magasin;
    }

    public CreateNewArticleController(Article article){
        this.article = article;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(this.article != null){
            this.lbNom.setText("Modifier l'article");
            this.validerArticle.setText("Modifier");
            this.nomArticle.setText(this.article.getName());
            this.DescArticle.setText(this.article.getDesc());
            this.prixArticle.setText(this.article.getPrice()+"");
        }
    }

    @FXML
    void imgArticleClick(ActionEvent event) {
        // Ouvrir la popup pour demander l'image
    }

    @FXML
    void validerArticleClick(ActionEvent event) {
        try{
            if(this.magasin != null) {
                // Vérifier que tous les champs sont remplis et que la dexcription fait moins de 200 caractères
                if(this.DescArticle.getText().length() < 199) {
                    if (this.prixArticle.getText().matches("[0-9]+(\\.[0-9]{1,2})?")) {
                        YesNoDialogController yesNoDialogController = ControllerUtils.loadYesNoDialog();
                        if (yesNoDialogController.getResponse()) {
                            ServerConstant.SERVER.addArticle(this.magasin.getId(), this.nomArticle.getText(), this.DescArticle.getText(), Double.parseDouble(this.prixArticle.getText()));
                            ControllerUtils.loadAlert("Créer un nouvel article", "Votre article a été créé avec succès");
                        }
                    } else {
                        ControllerUtils.loadAlert("Echec création d'un nouvel article", "Entrez un nombre cohérent");
                    }
                }else{
                    ControllerUtils.loadAlert("Echec création d'un nouvel article", "La description ne doit pas dépasser 200 caractères");
                }
            }

            if(this.article != null){
                ServerConstant.SERVER.updateArticle(this.article, this.nomArticle.getText(), this.DescArticle.getText(), Double.parseDouble(this.prixArticle.getText()));
                ControllerUtils.loadAlert("Modification d'article", "Votre article a été modifié avec succès");
            }
            closeClick(event);
        }catch (Exception ex){
            ControllerUtils.loadAlert("Echec d'une opération sur un article", ex.toString());
        }
    }

    @FXML
    void closeClick(ActionEvent event) {
        ((Stage) vbox.getScene().getWindow()).close();
    }
}
