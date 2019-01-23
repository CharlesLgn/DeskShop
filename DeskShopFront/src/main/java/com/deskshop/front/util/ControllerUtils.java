package com.deskshop.front.util;

import com.deskshop.front.controllers.DashboardController;
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

    public static void loadDashBoard(int userId) {
        try {
            DashboardController dashboardController = new DashboardController(userId);
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
