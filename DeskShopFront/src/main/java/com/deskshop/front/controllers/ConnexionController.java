package com.deskshop.front.controllers;
import com.deskshop.serv.Utils;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

public class ConnexionController {
    @FXML
    private VBox vbox;

    @FXML
    private JFXTextField mailaddressfield;

    @FXML
    private JFXTextField passwordfield;

    @FXML
    private JFXButton connexionbutton;

    @FXML
    private Hyperlink createAccount;

    @FXML
    void Enter(KeyEvent event) {

    }
    public static void main(String[] args){
        Utils.printHello();
    }
}
