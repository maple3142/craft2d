package net.maple3142.craft2d.item.tool;

import javafx.scene.image.Image;
import net.maple3142.craft2d.block.Block;
import net.maple3142.craft2d.block.Wooden;
import net.maple3142.craft2d.crafting.Recipe;
import net.maple3142.craft2d.crafting.SimpleRecipe;
import net.maple3142.craft2d.item.Breakable;
import net.maple3142.craft2d.item.Item;
import net.maple3142.craft2d.item.Tool;
import net.maple3142.craft2d.item.block.CobblestoneBlock;
import net.maple3142.craft2d.item.ingredient.IronIngot;
import net.maple3142.craft2d.item.ingredient.Stick;

public class IronAxe implements Item, Breakable, Tool {

    public static final int id = 23;

    public static final Recipe<IronAxe> recipe = new SimpleRecipe<>(IronAxe.class, 1, 2, 3,
            IronIngot.id, IronIngot.id,
            IronIngot.id, Stick.id,
            0, Stick.id);

    private final Image img = new Image(IronAxe.class.getResource("/item/iron_axe.png").toString());

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
        return item instanceof IronAxe;
    }

    @Override
    public int getFullDurability() {
        return 0;
    }

    @Override
    public int getDurability() {
        return 0;
    }

    @Override
    public boolean isBroken() {
        return false;
    }

    @Override
    public double getHardnessMultiplier(Block target) {
        var clz = target.getClass();
        if (Wooden.class.isAssignableFrom(clz)) { // clz implements Wooden
            return 0.35;
        }
        return 1;
    }
}
