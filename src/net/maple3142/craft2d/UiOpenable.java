package net.maple3142.craft2d;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

public interface UiOpenable {
    void drawUi(GraphicsContext ctx, double gameWidth, double gameHeight);

    void handleMousePress(MouseEvent event, double gameWidth, double gameHeight);

    void handleMouseMove(MouseEvent event, double gameWidth, double gameHeight);

    void handleMouseRelease(MouseEvent event, double gameWidth, double gameHeight);
}
