package net.maple3142.craft2d.block;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import net.maple3142.craft2d.item.ItemStack;
import net.maple3142.craft2d.item.Tool;
import net.maple3142.craft2d.item.ingredient.Coal;

public class CoalOre implements BreakableBlock {
    public static Image image = new Image(CoalOre.class.getResource("/block/coal_ore.png").toString());

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
        return 3.5;
    }

    @Override
    public ItemStack getDroppedItem(Tool brokeBy) {
        return new ItemStack(new Coal());
    }
}
