package com.deskshop.front.controllers;

import com.deskshop.common.metier.Article;
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
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;

import static com.deskshop.front.controllers.DashboardController.articleHashMap;

public class PanierController implements Initializable {

    @FXML
    private VBox vbox;

    @FXML
    private JFXButton bt_close;

    @FXML
    private JFXButton bt_commander;

    @FXML
    private ScrollPane scrollpane;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        scrollpane.setFitToHeight(true);
        scrollpane.setFitToWidth(true);
        FlowPane flowPane = new FlowPane();
        flowPane.setAlignment(Pos.CENTER);
        flowPane.setVgap(30);
        Iterator it = articleHashMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            System.out.println(pair.getKey() + " = " + pair.getValue());
            Pane pane = ControllerUtils.loadArticlePanier(((Article) pair.getKey()), ((int) pair.getValue()));
            flowPane.getChildren().add(pane);
            it.remove(); // avoids a ConcurrentModificationException
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
    }
}
