package net.maple3142.craft2d.item.tool;

import javafx.scene.image.Image;
import net.maple3142.craft2d.block.Block;
import net.maple3142.craft2d.block.StoneLike;
import net.maple3142.craft2d.crafting.Recipe;
import net.maple3142.craft2d.crafting.SimpleRecipe;
import net.maple3142.craft2d.item.Breakable;
import net.maple3142.craft2d.item.Item;
import net.maple3142.craft2d.item.Tool;
import net.maple3142.craft2d.item.block.CobblestoneBlock;
import net.maple3142.craft2d.item.ingredient.IronIngot;
import net.maple3142.craft2d.item.ingredient.Stick;

public class IronPickaxe implements Item, Breakable, Tool {

    public static final int id = 24;

    public static final Recipe<IronPickaxe> recipe = new SimpleRecipe<>(IronPickaxe.class, 1, 3, 3,
            IronIngot.id, IronIngot.id, IronIngot.id,
            0, Stick.id, 0,
            0, Stick.id, 0);

    private final Image img = new Image(IronPickaxe.class.getResource("/item/iron_pickaxe.png").toString());

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
        return item instanceof IronPickaxe;
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
        if (StoneLike.class.isAssignableFrom(clz)) {
            return 0.35;
        }
        return 1;
    }
}
