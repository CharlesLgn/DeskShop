package com.deskshop.front.controllers;
import com.deskshop.common.constant.ServerConstant;
import com.deskshop.common.link.ServerInterface;
import com.deskshop.front.util.ControllerUtils;
import com.deskshop.front.util.MoveUtils;
import com.deskshop.utils.HashPassword;
import com.deskshop.utils.XMLDataFinder;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.rmi.Naming;
import java.util.ResourceBundle;

public class ConnexionController implements Initializable {
    @FXML
    private VBox vbox;

    @FXML
    private JFXTextField mailaddressfield;

    @FXML
    private JFXPasswordField passwordfield;

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
            MoveUtils.moveEvent(vbox);
            ServerConstant.SERVER = (ServerInterface) Naming.lookup("//"+ServerConstant.IP+":"+ServerConstant.PORT+"/serverInterface");
            autoConnect();
            //lblEror.setVisible(false);
            mailaddressfield.setText(XMLDataFinder.getMail());
            passwordfield.setText("");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    public void createConnection() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/signup.fxml"));
        ControllerUtils.load(loader);
        ((Stage) vbox.getScene().getWindow()).close();
    }

    private void autoConnect() {
        try {
            String mail = XMLDataFinder.getMail();
            String pass = XMLDataFinder.getPassword();

            int res = ServerConstant.SERVER.login(mail, pass);
            if (res != -1) {
                Platform.runLater(() -> loadIRC(res));
            }
        } catch (Exception e) {
            //lblEror.setVisible(true);
        }
    }

    @FXML
    public void connect() {
        try {
            String mail = mailaddressfield.getText();
            String pass = passwordfield.getText();

            int res = ServerConstant.SERVER.login(mail, HashPassword.hash(pass));
            if (res == -1) {
                //lblEror.setVisible(true);
            } else {
                XMLDataFinder.setMail(mail);
                XMLDataFinder.setPassword(HashPassword.hash(pass));
                loadIRC(res);
            }
        } catch (Exception e) {
            //lblEror.setVisible(true);
        }
    }

    private void loadIRC(int userId) {
        ControllerUtils.loadChoix(userId);
        ((Stage) vbox.getScene().getWindow()).close();
    }

    @FXML
    void bt_close_action(ActionEvent event) {
        ((Stage) vbox.getScene().getWindow()).close();
    }

}
