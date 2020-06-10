package net.maple3142.craft2d.game.block;

import com.google.gson.annotations.Expose;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import net.maple3142.craft2d.game.Game;
import net.maple3142.craft2d.game.item.ItemStack;
import net.maple3142.craft2d.game.item.Tool;
import net.maple3142.craft2d.game.item.block.ChestBlock;
import net.maple3142.craft2d.game.ui.storage.ChestUi;

public class Chest implements BreakableBlock, Interactable, Wooden {

    public static Image image = new Image(Chest.class.getResource("/block/chest.png").toString());
    @Expose
    private final ItemStack[] storage = new ItemStack[27];

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
        return new ItemStack(new ChestBlock());
    }

    @Override
    public void onInteracted(Game game) {
        game.openUi(new ChestUi(storage));
    }
}
