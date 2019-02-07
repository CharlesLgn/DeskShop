package com.deskshop.front.controllers;

import com.deskshop.common.constant.ServerConstant;
import com.deskshop.common.metier.Person;
import com.deskshop.front.util.ControllerUtils;
import com.deskshop.front.util.MoveUtils;
import com.deskshop.utils.HashPassword;
import com.deskshop.utils.XMLDataFinder;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class SignupController implements Initializable {
    @FXML
    private VBox vbox;

    @FXML
    private JFXTextField fieldPrenom;

    @FXML
    private JFXTextField fieldNom;

    @FXML
    private JFXTextField fieldMail;

    @FXML
    private JFXPasswordField fieldMDP;

    @FXML
    private JFXPasswordField fieldMDPconfirm;

    @FXML
    private JFXButton btCreer;

    @FXML
    private Hyperlink link;

    private boolean melValidity;

    @FXML
    void Enter(KeyEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //lblEror.setVisible(false);
        MoveUtils.moveEvent(vbox);
    }

    @FXML
    public void goToConnection() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/connexion.fxml"));
        ControllerUtils.load(loader);
        ((Stage) vbox.getScene().getWindow()).close();
    }

    public void validate(){
        try {
            String psw1 = fieldMDP.getText();
            String psw2 = fieldMDPconfirm.getText();
            if (psw1.equals(psw2) && melValidity){
                Person user = new Person();
                user.setName(fieldNom.getText());
                user.setFirstName(fieldPrenom.getText());
                user.setPsw(HashPassword.hash(psw1));
                user.setMel(fieldMail.getText());
                int id = ServerConstant.SERVER.createUser(user);
                XMLDataFinder.setMail(user.getMel());
                XMLDataFinder.setPassword(user.getPsw());

                ControllerUtils.loadChoix2(id);
                ((Stage) vbox.getScene().getWindow()).close();

            } else {
                //lblEror.setVisible(true);
            }
        } catch (Exception e){
            //lblEror.setVisible(true);
            e.printStackTrace();
        }
    }

    @FXML
    public void validateEmail(){
        melValidity = fieldMail.getText().matches("^[a-z0-9]+([\\-.][a-z0-9]+)*@[a-z0-9]+([\\-.][a-z0-9]+)*\\.[a-z]{2,5}$");
        fieldMail.setFocusColor(melValidity ? Color.rgb(0, 255, 127) : Color.RED);
        fieldMail.setUnFocusColor(melValidity ? Color.rgb(0, 255, 127) : Color.RED);
    }

    @FXML
    void bt_close_action(ActionEvent event) {
        ((Stage) vbox.getScene().getWindow()).close();
    }
}

