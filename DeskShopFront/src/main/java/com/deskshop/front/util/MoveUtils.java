package com.deskshop.front.util;

import com.deskshop.front.start.Start;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public class MoveUtils {
    private static double xOffset = 0;
    private static double yOffset = 0;

    public static void moveEvent(Node node){
        node.setOnMousePressed(MoveUtils::mousePressed);
        node.setOnMouseDragged(MoveUtils::mouseDrag);
        node.setOnMouseReleased(e -> MoveUtils.mouseRelease());
    }

    /**
     * call to move the window
     */
    private static void mouseDrag(MouseEvent event) {
        setOpacity(0.8);
        Start.getPrimaryStage().setMaximized(false);
        if (Start.getPrimaryStage().getY() != event.getScreenY()) {
            Start.getPrimaryStage().setX(event.getScreenX() - xOffset);
            Start.getPrimaryStage().setY(event.getScreenY() - yOffset);
        }
    }

    /**
     * replace the window if it is out of the screen
     */
    private static void mouseRelease() {
        if (Start.getPrimaryStage().getY() < 0) {
            Start.getPrimaryStage().setY(0);
        }
        setOpacity(1);
    }

    /**
     * move the window
     */
    private static void mousePressed(MouseEvent event) {
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }

    private static void setOpacity(double opacity) {
        Start.getPrimaryStage().getScene().getWindow().setOpacity(opacity);
    }
}
