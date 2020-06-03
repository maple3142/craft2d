package net.maple3142.craft2d.block;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import net.maple3142.craft2d.item.ItemStack;
import net.maple3142.craft2d.item.Tool;

public class Bedrock implements Block {
    public static Image image = new Image(Bedrock.class.getResource("/block/bedrock.png").toString());

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
        return 1000000;
    }

    @Override
    public ItemStack getDroppedItem(Tool brokeBy) {
        return null;
    }
}
