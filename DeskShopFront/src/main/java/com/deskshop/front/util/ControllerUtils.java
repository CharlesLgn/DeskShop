package com.deskshop.front.util;

import com.deskshop.common.metier.Article;
import com.deskshop.common.metier.Compte;
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.List;

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

    public static void loadPopupArticle(Article article){
        try {
            PopupArticleController displayArticleController = new PopupArticleController(article);
            FXMLLoader loader = new FXMLLoader(ControllerUtils.class.getResource("/gui/popupArticle.fxml"));
            loader.setController(displayArticleController);
            loadFX(loader, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadPanier(){
        try {
            PanierController panierController = new PanierController();
            FXMLLoader loader = new FXMLLoader(ControllerUtils.class.getResource("/gui/panier.fxml"));
            loader.setController(panierController);
            loadFX(loader, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Pane loadDisplayArticle(Article article){
        try {
            DisplayArticleController displayArticleController = new DisplayArticleController(article);
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
            FXMLLoader loader = new FXMLLoader(ControllerUtils.class.getResource("/gui/displayCompteUser.fxml"));
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

            ResizeHelper.addResizeListener(Start.getPrimaryStage());

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
        st.initOwner(Start.getPrimaryStage().getScene().getWindow());
        st.initStyle(StageStyle.UNDECORATED);
        return st;
    }
}
