package net.maple3142.craft2d.block;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import net.maple3142.craft2d.item.ItemStack;
import net.maple3142.craft2d.item.Tool;

public class LeavesOak implements BreakableBlock, Wooden {
    public static Image image = new Image(LeavesOak.class.getResource("/block/leaves_oak.png").toString());

    @Override
    public Image getImage() {
        return image;
    }

    @Override
    public void draw(GraphicsContext ctx, int x, int y, int w, int h) {
        ctx.drawImage(image, x, y, w, h);
    }

    @Override
    public double getHardness() {
        return 0.2;
    }

    @Override
    public ItemStack getDroppedItem(Tool brokeBy) {
        return null;
    }
}
