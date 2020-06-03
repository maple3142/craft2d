package net.maple3142.craft2d.item.tool;

import javafx.scene.image.Image;
import net.maple3142.craft2d.block.Block;
import net.maple3142.craft2d.block.StoneLike;
import net.maple3142.craft2d.crafting.Recipe;
import net.maple3142.craft2d.crafting.SimpleRecipe;
import net.maple3142.craft2d.item.Breakable;
import net.maple3142.craft2d.item.Item;
import net.maple3142.craft2d.item.Tool;
import net.maple3142.craft2d.item.block.PlankOakBlock;
import net.maple3142.craft2d.item.ingredient.Stick;

public class WoodPickaxe implements Item, Breakable, Tool {

    public static final int id = 7;

    public static final Recipe<WoodPickaxe> recipe = new SimpleRecipe<>(WoodPickaxe.class, 1, 3, 3,
            PlankOakBlock.id, PlankOakBlock.id, PlankOakBlock.id,
            0, Stick.id, 0,
            0, Stick.id, 0);

    private final Image img = new Image(WoodPickaxe.class.getResource("/item/wood_pickaxe.png").toString());

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
        return item instanceof WoodPickaxe;
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
            return 0.5;
        }
        return 1;
    }
}
