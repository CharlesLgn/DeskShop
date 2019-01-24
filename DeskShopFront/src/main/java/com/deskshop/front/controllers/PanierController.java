package com.deskshop.front.controllers;

import com.deskshop.common.metier.Article;
import com.deskshop.front.util.ControllerUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;

import static com.deskshop.front.controllers.DashboardController.articleHashMap;

public class PanierController implements Initializable {

    @FXML
    private VBox vbox;

    @FXML
    private FlowPane flowpane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Iterator it = articleHashMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            System.out.println(pair.getKey() + " = " + pair.getValue());
            Pane pane = ControllerUtils.loadArticlePanier(((Article) pair.getKey()), ((int) pair.getValue()));
            flowpane.getChildren().add(pane);
            it.remove(); // avoids a ConcurrentModificationException
        }

    }
}
