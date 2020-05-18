package net.maple3142.craft2d;

import javafx.scene.canvas.GraphicsContext;

public interface UiOpenable {
    void drawUi(GraphicsContext ctx, double gameWidth, double gameHeight);
}
