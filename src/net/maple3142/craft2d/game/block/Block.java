package net.maple3142.craft2d.game.block;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public interface Block {
    Image getImage();

    void draw(GraphicsContext ctx, int x, int y, int width, int height);
}
