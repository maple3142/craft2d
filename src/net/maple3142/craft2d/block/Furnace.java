package net.maple3142.craft2d.block;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import net.maple3142.craft2d.Game;
import net.maple3142.craft2d.item.ItemStack;
import net.maple3142.craft2d.item.Tool;
import net.maple3142.craft2d.item.block.FurnaceBlock;

public class Furnace implements BreakableBlock, Interactable, StoneLike {

    public static Image imgOff = new Image(Furnace.class.getResource("/block/furnace_off.png").toString());

    public static Image imgOn = new Image(Furnace.class.getResource("/block/furnace_on.png").toString());

    public boolean on = false;

    @Override
    public Image getImage() {
        return on ? imgOn : imgOff;
    }

    @Override
    public void draw(GraphicsContext ctx, int x, int y, int w, int h) {
        ctx.drawImage(getImage(), x, y, w, h);
    }

    @Override
    public double getHardness() {
        return 2;
    }

    @Override
    public ItemStack getDroppedItem(Tool brokeBy) {
        return new ItemStack(new FurnaceBlock());
    }

    @Override
    public void onInteracted(Game game) {
        System.out.println("Furnace open");
    }
}
