package net.maple3142.craft2d.game.block;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import net.maple3142.craft2d.game.item.ItemStack;
import net.maple3142.craft2d.game.item.Tool;
import net.maple3142.craft2d.game.item.block.IronOreBlock;

public class IronOre implements BreakableBlock, StoneLike {
    public static Image image = new Image(IronOre.class.getResource("/block/iron_ore.png").toString());

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
        return 4;
    }

    @Override
    public ItemStack getDroppedItem(Tool brokeBy) {
        return new ItemStack(new IronOreBlock());
    }
}
