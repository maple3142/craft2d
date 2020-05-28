package net.maple3142.craft2d.item.tool;

import javafx.scene.image.Image;
import net.maple3142.craft2d.block.Block;
import net.maple3142.craft2d.block.CraftingTable;
import net.maple3142.craft2d.block.LogOak;
import net.maple3142.craft2d.block.PlankOak;
import net.maple3142.craft2d.crafting.Recipe;
import net.maple3142.craft2d.crafting.SimpleRecipe;
import net.maple3142.craft2d.item.Breakable;
import net.maple3142.craft2d.item.Item;
import net.maple3142.craft2d.item.Tool;
import net.maple3142.craft2d.item.block.PlankOakBlock;
import net.maple3142.craft2d.item.ingredient.Stick;

public class WoodAxe implements Item, Breakable, Tool {

    public static final int id = 7;

    public static final Recipe<WoodAxe> recipe = new SimpleRecipe<>(WoodAxe.class, 1, 2, 3,
            PlankOakBlock.id, PlankOakBlock.id,
            PlankOakBlock.id, Stick.id,
            0, Stick.id);

    private Image img = new Image(WoodAxe.class.getResource("/item/wood_axe.png").toString());

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
        if (clz == LogOak.class || clz == PlankOak.class || clz == CraftingTable.class) {
            return 0.5;
        }
        return 1;
    }
}
