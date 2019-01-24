package com.deskshop.front.controllers;

import com.deskshop.common.metier.Article;
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
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import static com.deskshop.front.controllers.DashboardController.articleHashMap;

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
    private Spinner<Integer> spinnerQte;

    private Article article;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.lbNomProduit.setText(this.article.getName());
        this.lb_Prix.setText(this.article.getPrice()+"");
        this.lb_desc.setText(this.article.getDesc());
        this.imgProduit.setImage(new Image("images/deskshop-logo.png"));
        spinnerQte.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,Integer.MAX_VALUE, 1,1));
    }

    public PopupArticleController(Article article) {
        this.article = article;
    }

    @FXML
    void imgProduitEnter(MouseEvent event) {

    }

    @FXML
    void imgProduitExited(MouseEvent event) {

    }

    @FXML
    void linkAddPanierClick(ActionEvent event) {
        articleHashMap.put(article, this.spinnerQte.getValue());
    }


    @FXML
    void btQuitterClick(ActionEvent event) {
        ((Stage) hbox.getScene().getWindow()).close();
    }
}
