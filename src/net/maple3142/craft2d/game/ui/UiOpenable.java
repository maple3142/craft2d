package net.maple3142.craft2d.game.ui;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import net.maple3142.craft2d.game.Game;
import net.maple3142.craft2d.game.utils.MouseTracker;

public interface UiOpenable {
    void onOpened(Game game);

    void onClosed(Game game);

    void drawUi(GraphicsContext ctx, MouseTracker mouse, double gameWidth, double gameHeight, Game game);

    void handleMousePressed(MouseEvent event, double gameWidth, double gameHeight, Game game);
}

