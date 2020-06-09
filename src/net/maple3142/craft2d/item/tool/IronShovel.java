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
import net.maple3142.craft2d.item.ingredient.IronIngot;
import net.maple3142.craft2d.item.ingredient.Stick;

public class IronShovel extends BasicBreakable implements Item, Tool {

    public static final int id = 25;

    public static final Recipe<IronShovel> recipe = new SimpleRecipe<>(IronShovel.class, 1, 1, 3,
            IronIngot.id,
            Stick.id,
            Stick.id);

    private final Image img = new Image(IronShovel.class.getResource("/item/iron_shovel.png").toString());

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
        return item instanceof IronShovel;
    }

    @Override
    public double getHardnessMultiplier(Block target) {
        var clz = target.getClass();
        if (clz == Dirt.class || clz == Grass.class) {
            return 0.35;
        }
        return 1;
    }

    @Override
    public int getFullDurability() {
        return 250;
    }
}
