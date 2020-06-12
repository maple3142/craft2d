package net.maple3142.craft2d.game.ui.pause;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import net.maple3142.craft2d.game.Game;
import net.maple3142.craft2d.game.ui.UiOpenable;
import net.maple3142.craft2d.game.utils.MouseTracker;

public class PauseUi implements UiOpenable {
    @Override
    public void onOpened(Game game) {

    }

    @Override
    public void onClosed(Game game) {

    }

    final public static double padding = 15;
    final public static double totalWidth = MinecraftCanvasButton.width;
    final public static double totalHeight = MinecraftCanvasButton.height * 2 + padding;

    private MinecraftCanvasButton back = new MinecraftCanvasButton("Back to Game");
    private MinecraftCanvasButton quit = new MinecraftCanvasButton("Save and Quit to Title");

    public PauseUi(Game game) {
        back.setOnClicked(game::closeUi);
        quit.setOnClicked(game::stop);
    }

    @Override
    public void drawUi(GraphicsContext ctx, MouseTracker mouse, double gameWidth, double gameHeight, Game game) {
        double X = (gameWidth - totalWidth) / 2;
        double Y = (gameHeight - totalHeight) / 2;

        back.setPosition(X, Y);
        quit.setPosition(X, Y + MinecraftCanvasButton.height + padding);

        back.draw(ctx, mouse);
        quit.draw(ctx, mouse);
    }

    @Override
    public void handleMousePressed(MouseEvent event, double gameWidth, double gameHeight, Game game) {
        back.handleMousePressed(event);
        quit.handleMousePressed(event);
    }
}
