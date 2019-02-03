package com.deskshop.front.controllers;

import com.deskshop.common.constant.ServerConstant;
import com.deskshop.front.util.ControllerUtils;
import com.deskshop.front.util.MoveUtils;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.sun.security.ntlm.Server;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ChoixTypeCompteController implements Initializable {

    @FXML
    private VBox vbox;

    @FXML
    private JFXComboBox comboBoxChoix;

    @FXML
    private JFXButton btSelection;

    private int nbUser;

    public ChoixTypeCompteController(int nbUser) {
        this.nbUser = nbUser;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            MoveUtils.moveEvent(vbox);
            List<String> actions = new ArrayList<>();
            actions.add("Consulter les magasins");
            actions.add("Gérer mes magasins");
            actions.add("Gérer mes comptes");
            if(ServerConstant.SERVER.isBanker(this.nbUser)){
                actions.add("Gérer mes comptes client");
            }

            this.comboBoxChoix.setItems(FXCollections.observableArrayList(actions));
            this.comboBoxChoix.getSelectionModel().select(0);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    @FXML
    void btSelectionClick(ActionEvent event) {
        try {
            ControllerUtils.loadDashBoard(nbUser, this.comboBoxChoix.getSelectionModel().getSelectedIndex());
            ((Stage) vbox.getScene().getWindow()).close();
        }catch (Exception ex){
            ControllerUtils.loadAlert("Erreur générale", ex.toString());
        }
    }

}
