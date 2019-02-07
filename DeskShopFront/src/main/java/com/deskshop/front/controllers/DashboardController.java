package com.deskshop.front.controllers;

import com.deskshop.common.constant.ServerConstant;
import com.deskshop.common.link.ClientInterface;
import com.deskshop.common.metier.Article;
import com.deskshop.common.metier.Compte;
import com.deskshop.common.metier.Magasin;
import com.deskshop.front.util.ControllerUtils;
import com.deskshop.front.util.MoveUtils;
import com.deskshop.utils.XMLDataFinder;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.Serializable;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
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
    private ScrollPane pnZoneTravail;
    @FXML
    private Label lbTitre, lblName;
    @FXML
    private JFXHamburger hamburger;
    @FXML
    private JFXDrawer drawer;
    @FXML
    private JFXButton btPanier;

    private int nbUser;
    private int indexComboBox;
    private Magasin actualShop;
    private HashMap<Article, Integer> panier = new HashMap<>();

    public DashboardController(int nbUser, int indexComboBox) {
        this.nbUser = nbUser;
        this.indexComboBox = indexComboBox;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            ClientInterface client = new ClientImpl();
            ServerConstant.SERVER.addObserver(client);
        } catch (Exception ex) {
            ControllerUtils.loadAlert("Erreur générale", ex.toString());
        }
        createDrawer();
        lblName.setText(XMLDataFinder.getMail());
        BorderPane.setAlignment(this.pnZoneTravail, Pos.CENTER);
        //addShop();
        //Si l'utilisateur clique sur la zone d'irc, le drawer se fermera
        pnZoneTravail.setOnMouseClicked(e -> drawer.close());
        //windows move management
        MoveUtils.moveEvent(mnuBar);
        pnPrincipal.getStylesheets().clear();
        pnPrincipal.getStylesheets().add(getClass().getResource("/gui/css/main-orange.css").toExternalForm());

        obtenirContexte(indexComboBox);
}

    /**
     * Affiche le panier
     * @param event
     */
    @FXML
    void btPanierClick(ActionEvent event) {
        ControllerUtils.loadPanier(panier, nbUser, actualShop, this);
    }

    private void obtenirContexte(int indexComboBox) {

        switch (indexComboBox) {
            case 0:
                // Consulter les magasins
                addAllShop();
                break;
            case 1:
                // Gérer mes magasins
                addMyShop();
                break;
            case 2:
                addMyAccountsdraw();
                // Gérer mes comptes
                break;
            case 3:
                addAllAccountsdraw();
                // Consulter les comptes clients
                break;
        }
    }

    private void addMyAccounts(){
        try {
            this.btPanier.setVisible(false);
            List<Compte> comptes = ServerConstant.SERVER.findAllCompteByUser(nbUser);
            fadeout(pnZoneTravail);
            pnZoneTravail.setFitToHeight(true);
            pnZoneTravail.setFitToWidth(true);
            FlowPane flowPane = new FlowPane();
            flowPane.setHgap(30);
            flowPane.setVgap(50);
            flowPane.setAlignment(Pos.CENTER);
            for (Compte compte : comptes) {
                Pane carteCompte = ControllerUtils.loadDisplayCompteUser(compte, comptes);
                flowPane.getChildren().add(carteCompte);
            }

            pnZoneTravail.setContent(flowPane);
            fadeout(pnZoneTravail);
        } catch (Exception ex) {
            ControllerUtils.loadAlert("Erreur générale", ex.toString());
        }
    }

    private void addAllAccounts(Compte compte, boolean allOrMy, List<Compte> comptes){
        try {
            fadeout(pnZoneTravail);
            pnZoneTravail.setFitToHeight(true);
            pnZoneTravail.setFitToWidth(true);
            FlowPane flowPane = new FlowPane();
            flowPane.setHgap(30);
            flowPane.setVgap(50);
            flowPane.setAlignment(Pos.CENTER);
            Pane carteCompte = null;
            if(!allOrMy) {
                carteCompte = ControllerUtils.loadDisplayCompte(compte);
            }else{
                carteCompte = ControllerUtils.loadDisplayCompteUser(compte, comptes);
            }
            flowPane.getChildren().add(carteCompte);

            pnZoneTravail.setContent(flowPane);
            fadeout(pnZoneTravail);
        } catch (Exception ex) {
            ControllerUtils.loadAlert("Erreur générale", ex.toString());
        }
    }

    private void addMyAccountsdraw(){
        try{
            this.btPanier.setVisible(false);
            addAccountsToDrawer(ServerConstant.SERVER.findAllCompteByUser(nbUser), true);
        } catch (Exception ex) {
            ControllerUtils.loadAlert("Erreur générale", ex.toString());
        }
    }

    private void addAllAccountsdraw(){
        try{
            this.btPanier.setVisible(false);
        addAccountsToDrawer(ServerConstant.SERVER.findAllCompte(), false);
        } catch (Exception ex) {
            ControllerUtils.loadAlert("Erreur générale", ex.toString());
        }
    }

    private void addAccountsToDrawer(List<Compte> comptes, boolean allOrMy) {
        VBox vBox = ControllerUtils.createVBox();
        for (Compte compte : comptes) {
            JFXButton jfxButton = new JFXButton(compte.getName() + "\n" + compte.getClient().getMel());
            jfxButton.getStyleClass().add("button");
            jfxButton.setPrefSize(Double.MAX_VALUE, 80);
            jfxButton.setOnAction(event -> {
                // Vérifier à l'aide d'une requête la connexion au serveur
                addAllAccounts(compte, allOrMy, comptes);
            lbTitre.setText(compte.getName());
            });

            vBox.getChildren().add(jfxButton);
        }

        vBox.getChildren().add(addcomptebutton(allOrMy));


        ScrollPane scrollPane = new ScrollPane();
        scrollPane.getStyleClass().add("menu-bar-2");
        scrollPane.setFitToWidth(true);
        scrollPane.setContent(vBox);

        VBox slider = ControllerUtils.createVBox();

        //slider.getChildren().add(iw);
        slider.getChildren().add(scrollPane);

        //drawer.setSidePane(scrollPane);
        drawer.setSidePane(slider);
    }


    private void addAllShop() {
        try {
            addShop(ServerConstant.SERVER.findAllMagasin(nbUser), false);
        } catch (Exception ex) {
            ControllerUtils.loadAlert("Erreur générale", ex.toString());
        }
    }

    private void addMyShop() {
        try {
            addShop(ServerConstant.SERVER.findMagasinByUser(nbUser), true);
        } catch (Exception ex) {
            ControllerUtils.loadAlert("Erreur générale", ex.toString());
        }
    }

    private JFXButton addbutton(){
        JFXButton addbutton = new JFXButton("+");
        addbutton.setButtonType(JFXButton.ButtonType.RAISED);
        addbutton.getStyleClass().add("button");
        addbutton.setPrefSize(60, 60);
        return  addbutton;
    }

    private JFXButton addmagasinbutton(){
        JFXButton addnewserver = addbutton();
        addnewserver.setOnAction(e -> addmagasin());
        return addnewserver;
    }

    private JFXButton addarticlebutton(){
        JFXButton addnewarticle = addbutton();
        addnewarticle.setOnAction(e -> addarticle());
        return addnewarticle;
    }

    private JFXButton addcomptebutton(boolean allOrMy){
        JFXButton addCompte = addbutton();
        addCompte.setOnAction(e -> addcompte(allOrMy));
        return addCompte;
    }

    private void addcompte(boolean allOrMy) { ControllerUtils.loadCreateNewCompte(this.nbUser, allOrMy); }

    private void addarticle() {ControllerUtils.loadCreateNewArticle(this.actualShop); }

    private void addmagasin() {
        ControllerUtils.loadCreateNewShop(this.nbUser);
    }

    private void addShop(List<Magasin> magasinList, boolean addmagasin) {
        VBox vBox = ControllerUtils.createVBox();
        for (Magasin magasin : magasinList) {
            JFXButton jfxButton = new JFXButton(magasin.getName());
            jfxButton.getStyleClass().add("button");
            jfxButton.setPrefSize(Double.MAX_VALUE, 60);
            jfxButton.setOnAction(event -> {
                // Vérifier à l'aide d'une requête la connexion au serveur
                showShop(magasin, addmagasin);
                actualShop = magasin;
                lbTitre.setText(magasin.getName());
            });

            vBox.getChildren().add(jfxButton);
        }

        if(addmagasin){
            this.btPanier.setVisible(false);
            vBox.getChildren().add(addmagasinbutton());
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
     * call when we need to show a shop
     * Affiche les produits
     */
    private void showShop(Magasin magasin, boolean addnewArticle) {
        try {
            // Les articles dans le panier sont retirés
            panier.clear();
            fadeout(pnZoneTravail);
            pnZoneTravail.setFitToHeight(true);
            pnZoneTravail.setFitToWidth(true);
            List<Article> articles = ServerConstant.SERVER.getArticleByMagasin(magasin.getId());
            FlowPane flowPane = new FlowPane();
            flowPane.setHgap(30);
            flowPane.setVgap(50);
            flowPane.setAlignment(Pos.CENTER);
            for (Article article: articles) {
                Pane carteArticle = ControllerUtils.loadDisplayArticle(article, panier, addnewArticle);
                flowPane.getChildren().add(carteArticle);
            }

            if(addnewArticle){
            flowPane.getChildren().add(addarticlebutton());
            }

            pnZoneTravail.setContent(flowPane);
            fadeout(pnZoneTravail);
        }catch (Exception ex){
            ControllerUtils.loadAlert("Erreur générale", ex.toString());
        }
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
    private void fadeout(ScrollPane pane) {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(250), pane);
        fadeTransition.setNode(pane);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.setCycleCount(2);
        fadeTransition.setAutoReverse(true);
        fadeTransition.play();
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
            ControllerUtils.loadAlert("Erreur générale", ex.toString());
        }
    }

    @FXML
    public void changeLanguage(ActionEvent event) {

    }

    class ClientImpl extends UnicastRemoteObject implements ClientInterface, Serializable {
            ClientImpl() throws RemoteException {
                super();
            }

        private static final long serialVersionUID = 1L;

        @Override
        public void update(Object observable, Object updateMsg) {
            Platform.runLater(()->{
                obtenirContexte(indexComboBox);
            });
        }
    }

    public void viderPanier(){
        this.panier.clear();
    }
}