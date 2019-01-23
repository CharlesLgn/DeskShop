package com.deskshop.front.controllers;

import com.deskshop.common.constant.ServerConstant;
import com.deskshop.common.metier.Magasin;
import com.deskshop.front.start.Start;
import com.deskshop.front.util.ControllerUtils;
import com.deskshop.front.util.MoveUtils;
import com.deskshop.utils.XMLDataFinder;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.List;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    private MenuBar mnuBar;
    @FXML
    private Menu mnuMenu, mnuLanguage;
    @FXML
    private Menu mnuStyle, mnuHelp;
    @FXML
    private MenuItem mnuAbout, whiteTheme, blackTheme, pinkTheme, greenTheme;
    @FXML
    private GridPane pnPrincipal;
    @FXML
    private BorderPane pnZoneTravail;
    @FXML
    private Label lbTitre, lblName;
    @FXML
    private JFXHamburger hamburger;
    @FXML
    private JFXDrawer drawer;

    private int nbUser;

    public DashboardController(int nbUser) {
        this.nbUser = nbUser;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        createDrawer();
        lblName.setText(XMLDataFinder.getMail());
        BorderPane.setAlignment(this.pnZoneTravail, Pos.CENTER);
        addShop();
        //Si l'utilisateur clique sur la zone d'irc, le drawer se fermera
        pnZoneTravail.setOnMouseClicked(e -> drawer.close());
        //windows move management
        MoveUtils.moveEvent(mnuBar);
        pnPrincipal.getStylesheets().clear();
        pnPrincipal.getStylesheets().add(getClass().getResource("/gui/css/main-orange.css").toExternalForm());
    }

    private void addShop() {
        List<Magasin> magasinList;
        try {
            magasinList = ServerConstant.SERVER.findAllMagasin();
            VBox vBox = ControllerUtils.createVBox();

            /*ImageView iw = new ImageView();
            Image logo = new Image("image/logov4.png", true);
            iw.setImage(logo);
            iw.setFitHeight(34.5);
            iw.setFitWidth(150);
*/
            for (Magasin magasin : magasinList) {
                JFXButton jfxButton = new JFXButton(magasin.getName());
                jfxButton.getStyleClass().add("button");
                jfxButton.setPrefSize(Double.MAX_VALUE, 60);
                jfxButton.setOnAction(event -> {
                    // Vérifier à l'aide d'une requête la connexion au serveur
                    AffichageIRCClick(magasin.getId());
                    lbTitre.setText(magasin.getName());
                });

                vBox.getChildren().add(jfxButton);
            }

            ScrollPane scrollPane = new ScrollPane();
            scrollPane.getStyleClass().add("menu-bar-2");
            scrollPane.setFitToWidth(true);
            scrollPane.setContent(vBox);

            VBox slider = ControllerUtils.createVBox();

            //slider.getChildren().add(iw);
            slider.getChildren().add(scrollPane);

            //drawer.setSidePane(scrollPane);
            drawer.setSidePane(slider);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create a drawer with all these event
     */
    private void createDrawer() {
        HamburgerBackArrowBasicTransition burgertask = new HamburgerBackArrowBasicTransition(hamburger);
        burgertask.setRate(-1);
        drawer.setOnDrawerClosing(e -> {
            drawer.setOnDrawerClosed(e2 -> {
                drawer.setPrefWidth(0);
                drawer.setMinWidth(0);
                drawer.setMaxWidth(0);
            });
            this.pnZoneTravail.setPadding(new Insets(0, 0, 0, 0));
            changeBurger(burgertask);
        });

        drawer.setOnDrawerOpening(e -> open(burgertask));

        hamburger.setOnMousePressed(e -> {
            if (drawer.isOpened()) {
                drawer.close();
            } else {
                drawer.toggle();
            }
        });
        drawer.toggle();
    }

    private void open(HamburgerBackArrowBasicTransition burgertask) {
        drawer.setPrefWidth(260);
        drawer.setMinWidth(260);
        drawer.setMaxWidth(260);
        this.pnZoneTravail.setPadding(new Insets(0, 0, 0, 260));
        changeBurger(burgertask);
    }

    /**
     * call when we need to show an IRC
     */
    private void AffichageIRCClick(int nbServ) {
        fadeout(pnZoneTravail);
        fadeout(pnZoneTravail);

    }

    /**
     * change the menu icon to a row when it is open
     * change the row to a menu icon when it is close
     */
    private void changeBurger(HamburgerBackArrowBasicTransition burgertask) {
        burgertask.setRate(burgertask.getRate() * -1);
        burgertask.play();
    }

    /**
     * transition effect
     *
     * @param pane : Pane (Work Zone) where it will display the content
     */
    private void fadeout(Pane pane) {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(250), pane);
        fadeTransition.setNode(pane);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.setCycleCount(2);
        fadeTransition.setAutoReverse(true);
        fadeTransition.play();
    }

    /**
     * maximized the window
     */
    private void mouseClicked(MouseEvent event) {
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            if (event.getClickCount() == 2) {
                Start.getPrimaryStage().setMaximized(!Start.getPrimaryStage().isMaximized());
            }
        }
    }

    /**
     * close application
     */
    public void close() {
        ((Stage) pnPrincipal.getScene().getWindow()).close();
    }

    /**
     * maximize palication size
     */
    @FXML
    public void Maximize() {
        if (((Stage) pnPrincipal.getScene().getWindow()).isMaximized()) {
            ((Stage) pnPrincipal.getScene().getWindow()).setMaximized(false);
        } else {
            ((Stage) pnPrincipal.getScene().getWindow()).setMaximized(true);
        }
    }


    /**
     * shrink application
     */
    @FXML
    public void Shrink() {
        ((Stage) pnPrincipal.getScene().getWindow()).setIconified(true);
    }

    @FXML
    public void disconnect() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/connexion.fxml"));
        XMLDataFinder.setPassword("");
        ControllerUtils.load(loader);
        ((Stage) pnPrincipal.getScene().getWindow()).close();
    }

    /**
     * generate the about window
     */
    @FXML
    public void about() {
        try {
            Stage st = new Stage();
            st.initModality(Modality.NONE);
            st.initOwner(pnPrincipal.getScene().getWindow());
            st.initStyle(StageStyle.UNDECORATED);

            Parent root = FXMLLoader.load(getClass().getResource("/gui/propos.fxml"));
            Scene scene = new Scene(root);
            st.setScene(scene);
            st.setResizable(false);
            st.show();
            st.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
                if (!newPropertyValue) {
                    st.close();
                }
            });

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    public void changeLanguage(ActionEvent event) {

    }
}