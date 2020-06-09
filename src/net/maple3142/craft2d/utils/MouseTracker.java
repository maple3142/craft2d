package net.maple3142.craft2d.utils;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public class MouseTracker {
    private double x;
    private double y;
    private boolean primaryPressed = false;
    private boolean secondaryPressed = false;

    public void bind(Node node) {
        node.addEventHandler(MouseEvent.MOUSE_PRESSED, this::update);
        node.addEventHandler(MouseEvent.MOUSE_RELEASED, this::update);
        node.addEventHandler(MouseEvent.MOUSE_MOVED, this::update);
        node.addEventHandler(MouseEvent.MOUSE_DRAGGED, this::update);
    }

    public void update(MouseEvent event) {
        x = event.getX();
        y = event.getY();
        if (event.isPrimaryButtonDown()) {
            primaryPressed = true;
            secondaryPressed = false;
        } else if (event.isSecondaryButtonDown()) {
            primaryPressed = false;
            secondaryPressed = true;
        } else {
            primaryPressed = secondaryPressed = false;
        }
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public boolean isPrimaryPressed() {
        return primaryPressed;
    }

    public boolean isSecondaryPressed() {
        return secondaryPressed;
    }

}
