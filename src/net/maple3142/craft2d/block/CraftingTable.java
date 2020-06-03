package net.maple3142.craft2d.block;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import net.maple3142.craft2d.Game;
import net.maple3142.craft2d.item.ItemStack;
import net.maple3142.craft2d.item.Tool;
import net.maple3142.craft2d.item.block.CraftingTableBlock;
import net.maple3142.craft2d.ui.crafting.CraftingTableUi;

public class CraftingTable implements BreakableBlock, Interactable, Wooden {
    public static Image image = new Image(CraftingTable.class.getResource("/block/crafting_table.png").toString());

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
        return 2;
    }

    @Override
    public ItemStack getDroppedItem(Tool brokeBy) {
        return new ItemStack(new CraftingTableBlock());
    }

    @Override
    public void onInteracted(Game game) {
        game.openUi(new CraftingTableUi());
    }
}
