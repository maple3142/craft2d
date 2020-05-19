package net.maple3142.craft2d;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

public interface UiOpenable {
    void drawUi(GraphicsContext ctx, MouseTracker mouse, double gameWidth, double gameHeight);

    void handleMousePressed(MouseEvent event, double gameWidth, double gameHeight);
}
