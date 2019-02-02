package com.deskshop.front.util;

import com.deskshop.common.constant.ServerConstant;
import com.deskshop.common.metier.Article;
import com.deskshop.common.metier.Compte;
import com.deskshop.common.metier.Magasin;
import com.deskshop.common.metier.Movement;
import com.deskshop.front.controllers.*;
import com.deskshop.front.start.Start;
import com.deskshop.utils.ResizeHelper;
import com.deskshop.utils.XMLDataFinder;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static com.deskshop.front.start.Start.getPrimaryStage;

/**
 * all function used in the IRC controller but which could be use somewhere else
 */
public class ControllerUtils {

    public static void loadStart(FXMLLoader loader){
        loadFX(loader,0);
    }

    public static void load(FXMLLoader loader) {
        loadFX(loader, 1);
    }

    public static void loadChoix(int userId){
        try {
            ChoixTypeCompteController choixTypeCompteController= new ChoixTypeCompteController(userId);
            FXMLLoader loader = new FXMLLoader(ControllerUtils.class.getResource("/gui/choixtypecompte.fxml"));
            loader.setController(choixTypeCompteController);
            loadFX(loader, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadCreateNewShop(int userId){
        try {
            CreateNewShopController createNewShopController = new CreateNewShopController(userId);
            FXMLLoader loader = new FXMLLoader(ControllerUtils.class.getResource("/gui/createNewShop.fxml"));
            loader.setController(createNewShopController);
            loadFX(loader, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadAlert(String titre, String message){
        try {
            AlertController alertController = new AlertController(titre, message);
            FXMLLoader loader = new FXMLLoader(ControllerUtils.class.getResource("/gui/genericAlert.fxml"));
            loader.setController(alertController);
            loadFX(loader, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static YesNoDialogController loadYesNoDialog(){
            YesNoDialogController yesNoDialogController = new YesNoDialogController();
            FXMLLoader loader = new FXMLLoader(ControllerUtils.class.getResource("/gui/yesNoDialog.fxml"));
            loader.setController(yesNoDialogController);
            loadFXwait(loader, 1);
            return yesNoDialogController;
    }

    public static void loadCreateNewArticle(Magasin magasin){
        try {
            CreateNewArticleController createNewArticleController = new CreateNewArticleController(magasin);
            FXMLLoader loader = new FXMLLoader(ControllerUtils.class.getResource("/gui/createNewArticle.fxml"));
            loader.setController(createNewArticleController);
            loadFX(loader, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadCreateNewArticle(Article article){
        try {
            CreateNewArticleController createNewArticleController = new CreateNewArticleController(article);
            FXMLLoader loader = new FXMLLoader(ControllerUtils.class.getResource("/gui/createNewArticle.fxml"));
            loader.setController(createNewArticleController);
            loadFX(loader, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadPopupArticle(Article article, HashMap<Article, Integer> panier, boolean modifyArticle){
        try {
            PopupArticleController displayArticleController = new PopupArticleController(article, panier, modifyArticle);
            FXMLLoader loader = new FXMLLoader(ControllerUtils.class.getResource("/gui/popupArticle.fxml"));
            loader.setController(displayArticleController);
            loadFX(loader, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadPanier(HashMap<Article, Integer> panier, int nbUser, Magasin shop){
        try {
            PanierController panierController = new PanierController(panier, nbUser, shop);
            FXMLLoader loader = new FXMLLoader(ControllerUtils.class.getResource("/gui/panier.fxml"));
            loader.setController(panierController);
            loadFX(loader, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Pane loadDisplayArticle(Article article, HashMap<Article, Integer> panier, boolean modifyArticle){
        try {
            DisplayArticleController displayArticleController = new DisplayArticleController(article, panier, modifyArticle);
            FXMLLoader loader = new FXMLLoader(ControllerUtils.class.getResource("/gui/displayArticle.fxml"));
            loader.setController(displayArticleController);
            Pane root = loader.load();
            return root;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Pane loadDisplayCompte(Compte compte) {
        try {
            DisplayCompteController displayCompteController = new DisplayCompteController(compte);
            FXMLLoader loader = new FXMLLoader(ControllerUtils.class.getResource("/gui/displayCompte.fxml"));
            loader.setController(displayCompteController);
            Pane root = loader.load();
            return root;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Pane loadDisplayCompteUser(Compte compte, List<Compte> comptes) {
        try {
            DisplayCompteUserController displayCompteUserController = new DisplayCompteUserController(compte, comptes);
            FXMLLoader loader = new FXMLLoader(ControllerUtils.class.getResource("/gui/displayCompte.fxml"));
            loader.setController(displayCompteUserController);
            Pane root = loader.load();
            return root;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Pane loadArticlePanier(Article article, int qte){
        try {
            ArticlePanierController articlePanierController = new ArticlePanierController(article, qte);
            FXMLLoader loader = new FXMLLoader(ControllerUtils.class.getResource("/gui/ArticlePanier.fxml"));
            loader.setController(articlePanierController);
            Pane root = loader.load();
            return root;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public static void loadDashBoard(int userId, int indexComboBox) {
        try {
            DashboardController dashboardController = new DashboardController(userId, indexComboBox);
            FXMLLoader loader = new FXMLLoader(ControllerUtils.class.getResource("/gui/dashboard.fxml"));
            loader.setController(dashboardController);
            loadFX(loader, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * to load an fxml
     */
    private static void loadFX(FXMLLoader loader, int index) {
        try {
            Pane root = loader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.NONE);
            //stage.initOwner(getPrimaryStage());
            stage.initStyle(StageStyle.UNDECORATED);
            assert root != null;
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("DeskShop");
            //stage.getIcons().add(new Image("image/ediome.png"));
            scene.getStylesheets().add("gui/css/main-" + XMLDataFinder.getTheme() + ".css");
            stage.setResizable(true);
            stage.show();
            Start.setPrimaryStage(stage);

            windowsBehavior(index);

            ResizeHelper.addResizeListener(getPrimaryStage());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * to load an fxml
     */
    private static void loadFXwait(FXMLLoader loader, int index) {
        try {
            Pane root = loader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.NONE);
            //stage.initOwner(getPrimaryStage());
            stage.initStyle(StageStyle.UNDECORATED);
            assert root != null;
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("DeskShop");
            //stage.getIcons().add(new Image("image/ediome.png"));
            scene.getStylesheets().add("gui/css/main-" + XMLDataFinder.getTheme() + ".css");
            stage.setResizable(true);
            stage.showAndWait();
            Start.setPrimaryStage(stage);

            windowsBehavior(index);

            ResizeHelper.addResizeListener(getPrimaryStage());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void windowsBehavior(int index){
        //windows behavior
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            long lhwnd = com.sun.glass.ui.Window.getWindows().get(index).getNativeWindow();
            Pointer lpVoid = new Pointer(lhwnd);
            WinDef.HWND hwnd = new WinDef.HWND(lpVoid);
            final User32 user32 = User32.INSTANCE;
            int oldStyle = user32.GetWindowLong(hwnd, WinUser.GWL_STYLE);
            int newStyle = oldStyle | 0x00020000;//WS_MINIMIZEBOX
            user32.SetWindowLong(hwnd, WinUser.GWL_STYLE, newStyle);
        }
    }

    public static VBox createVBox(){
        VBox vBox = new VBox();
        vBox.getStyleClass().add("menu-bar-2");
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(10, 10, 20, 10));
        vBox.setAlignment(Pos.TOP_CENTER);
        return vBox;
    }

    public static Stage createStage(){
        Stage st = new Stage();
        st.initModality(Modality.WINDOW_MODAL);
        st.initOwner(getPrimaryStage().getScene().getWindow());
        st.initStyle(StageStyle.UNDECORATED);
        return st;
    }

    public static VBox generateMovements(VBox vBox, Compte compte){
        try {
            List<Movement> movementList = ServerConstant.SERVER.findMovementByCompte(compte);
            vBox.getChildren().clear();
            for (Movement mov : movementList) {
                HBox movementDesc = new HBox();
                movementDesc.setSpacing(40);
                Label labeldate = new Label("Date :");
                Label labeldatedisplay = new Label(mov.getDate() + "");
                Label labelmontant = new Label("Montant :");
                Label labelmontantdisplay = new Label(mov.getAmount() + "");
                if (mov.getAmount() < 0) {
                    labelmontantdisplay.getStyleClass().add("movement-neg");
                } else {
                    labelmontantdisplay.getStyleClass().add("movement-pos");
                }
                movementDesc.getChildren().add(labeldate);
                movementDesc.getChildren().add(labeldatedisplay);
                movementDesc.getChildren().add(labelmontant);
                movementDesc.getChildren().add(labelmontantdisplay);
                movementDesc.setMargin(labeldate, new Insets(0, 0, 0, 20));
                vBox.getChildren().add(movementDesc);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return vBox;
    }
}
