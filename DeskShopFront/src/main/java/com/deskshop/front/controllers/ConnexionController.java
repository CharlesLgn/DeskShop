package com.deskshop.front.controllers;
import com.deskshop.common.link.ClientInterface;
import com.deskshop.common.link.ServerInterface;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

import java.io.Serializable;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ResourceBundle;

public class ConnexionController implements Initializable {
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {

            ServerInterface remoteService = (ServerInterface) Naming.lookup("//localhost:720/serverInterface");

            ClientInterface client = new ClientImpl();
            remoteService.addObserver(client);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private class ClientImpl extends UnicastRemoteObject implements ClientInterface, Serializable {
        public ClientImpl() throws RemoteException {
            super();
        }

        private static final long serialVersionUID = 1L;

        @Override
        public void update(Object observable, Object updateMsg) {
            mailaddressfield.setText(updateMsg.toString());
        }
    }
}
