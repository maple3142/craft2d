package net.maple3142.craft2d.block;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import net.maple3142.craft2d.item.ItemStack;
import net.maple3142.craft2d.item.Tool;

public interface Block {
    Image getImage();

    void draw(GraphicsContext ctx, int x, int y, int width, int height);
}
