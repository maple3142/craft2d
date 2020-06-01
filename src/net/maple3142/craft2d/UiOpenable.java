package net.maple3142.craft2d;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import net.maple3142.craft2d.entity.Player;

public interface UiOpenable {
    void onOpened(Player player);

    void onClosed(Player player);

    void drawUi(GraphicsContext ctx, MouseTracker mouse, double gameWidth, double gameHeight, Player player);

    void handleMousePressed(MouseEvent event, double gameWidth, double gameHeight, Player player);
}
