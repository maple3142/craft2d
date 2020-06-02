package net.maple3142.craft2d.ui;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import net.maple3142.craft2d.Game;
import net.maple3142.craft2d.MouseTracker;

public interface UiOpenable {
    void onOpened(Game game);

    void onClosed(Game game);

    void drawUi(GraphicsContext ctx, MouseTracker mouse, double gameWidth, double gameHeight, Game game);

    void handleMousePressed(MouseEvent event, double gameWidth, double gameHeight, Game game);
}
