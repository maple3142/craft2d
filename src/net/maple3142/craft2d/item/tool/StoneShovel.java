package net.maple3142.craft2d.item.tool;

import javafx.scene.image.Image;
import net.maple3142.craft2d.block.Block;
import net.maple3142.craft2d.block.Dirt;
import net.maple3142.craft2d.block.Grass;
import net.maple3142.craft2d.crafting.Recipe;
import net.maple3142.craft2d.crafting.SimpleRecipe;
import net.maple3142.craft2d.item.Breakable;
import net.maple3142.craft2d.item.Item;
import net.maple3142.craft2d.item.Tool;
import net.maple3142.craft2d.item.block.CobblestoneBlock;
import net.maple3142.craft2d.item.block.PlankOakBlock;
import net.maple3142.craft2d.item.ingredient.Stick;

public class StoneShovel implements Item, Breakable, Tool {

    public static final int id = 19;

    public static final Recipe<StoneShovel> recipe = new SimpleRecipe<>(StoneShovel.class, 1, 1, 3,
            CobblestoneBlock.id,
            Stick.id,
            Stick.id);

    private final Image img = new Image(StoneShovel.class.getResource("/item/stone_shovel.png").toString());

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
        return item instanceof StoneShovel;
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
        if (clz == Dirt.class || clz == Grass.class) {
            return 0.5;
        }
        return 1;
    }
}