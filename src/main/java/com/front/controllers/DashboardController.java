package com.front.controllers;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
public class DashboardController {


    @FXML
    private GridPane pnPrincipal;

    @FXML
    private GridPane fullmenubar;

    @FXML
    private MenuBar mnuBar;

    @FXML
    private Menu mnuMenu;

    @FXML
    private Menu mnuStyle;

    @FXML
    private MenuItem whiteTheme;

    @FXML
    private MenuItem blackTheme;

    @FXML
    private MenuItem blueTheme;

    @FXML
    private MenuItem greenTheme;

    @FXML
    private MenuItem pinkTheme;

    @FXML
    private Menu mnuLanguage;

    @FXML
    private MenuItem mnuDe;

    @FXML
    private MenuItem mnuEn;

    @FXML
    private MenuItem mnuEs;

    @FXML
    private MenuItem mnuFr;

    @FXML
    private MenuItem mnuRu;

    @FXML
    private Menu mnuHelp;

    @FXML
    private MenuItem mnuAbout;

    @FXML
    private JFXHamburger hamburger;

    @FXML
    private Label lbTitre;

    @FXML
    private Label lblName;

    @FXML
    private BorderPane pnZoneTravail;

    @FXML
    private JFXDrawer drawer;

    @FXML
    void Maximize(ActionEvent event) {

    }

    @FXML
    void Shrink(ActionEvent event) {

    }

    @FXML
    void about(ActionEvent event) {

    }

    @FXML
    void changeLanguage(ActionEvent event) {

    }

    @FXML
    void changeTheme(ActionEvent event) {

    }

    @FXML
    void close(ActionEvent event) {

    }

    @FXML
    void disconnect(ActionEvent event) {

    }
}
