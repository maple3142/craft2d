package net.maple3142.craft2d.item.tool;

import javafx.scene.image.Image;
import net.maple3142.craft2d.block.Block;
import net.maple3142.craft2d.block.Wooden;
import net.maple3142.craft2d.crafting.Recipe;
import net.maple3142.craft2d.crafting.SimpleRecipe;
import net.maple3142.craft2d.item.Breakable;
import net.maple3142.craft2d.item.Item;
import net.maple3142.craft2d.item.Tool;
import net.maple3142.craft2d.item.block.PlankOakBlock;
import net.maple3142.craft2d.item.ingredient.Stick;

public class WoodAxe extends BasicBreakable implements Item, Tool {

    public static final int id = 7;

    public static final Recipe<WoodAxe> recipe = new SimpleRecipe<>(WoodAxe.class, 1, 2, 3,
            PlankOakBlock.id, PlankOakBlock.id,
            PlankOakBlock.id, Stick.id,
            0, Stick.id);

    private final Image img = new Image(WoodAxe.class.getResource("/item/wood_axe.png").toString());

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Image getImage() {
        return img;
    }

    @Override
    public boolean equals(Item item) {
        return item instanceof WoodAxe;
    }

    @Override
    public double getHardnessMultiplier(Block target) {
        var clz = target.getClass();
        if (Wooden.class.isAssignableFrom(clz)) { // clz implements Wooden
            return 0.7;
        }
        return 1;
    }

    @Override
    public int getFullDurability() {
        return 59;
    }
}
